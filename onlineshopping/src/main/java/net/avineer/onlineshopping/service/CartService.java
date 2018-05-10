package net.avineer.onlineshopping.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.avineer.onlineshopping.model.UserModel;
import net.avineer.shoppingbackend.dao.CartLineDAO;
import net.avineer.shoppingbackend.dao.ProductDAO;
import net.avineer.shoppingbackend.dto.Cart;
import net.avineer.shoppingbackend.dto.CartLine;
import net.avineer.shoppingbackend.dto.Product;

@Service("cartService")
public class CartService {
	
	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private HttpSession session;
	
	// return the Cart of the user who has logged in
	private Cart getCart() {		
		return ((UserModel)session.getAttribute("userModel")).getCart();		
	}
	
	// Returns the entire cart lines
	public List<CartLine> getCartLines () {
		return cartLineDAO.list(this.getCart().getId());		
	}

	public String manageCartLine(int cartLineId, int count) {
		
		// fetch the cart Line		
		CartLine cartLine = cartLineDAO.get(cartLineId);
		if (cartLine == null) {
			return "result=error";
		}
		else {
			Product product = cartLine.getProduct();
			double oldTotal = cartLine.getTotal();
			
			// check if that much quantity is available or not
			if(product.getQuantity() < count) {
				return "result=unavailable";		
			}	
			
			cartLine.setProductCount(count);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice() * count);			
			cartLineDAO.update(cartLine);			
			// Calculate the new cart grand total and update
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());			
			cartLineDAO.updateCart(cart);
			
			return "result=updated";
		}
	}

	public String deleteCartLine(int cartLineId) {
		CartLine cartLine = cartLineDAO.get(cartLineId);
		if (cartLine == null) {
			return "result=error";
		}
		else {		
			Cart cart = this.getCart();
			
			cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());	
			cart.setCartLines(cart.getCartLines() - 1);
			cartLineDAO.updateCart(cart);
			
			cartLineDAO.remove(cartLine);
			
			return "result=deleted";
		}
	}

	public String addCartLine(int productId) {
		
		String response = null;
		// get cart from the session
		Cart cart = this.getCart();
		CartLine cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), productId);
		
		if (cartLine == null) {
			// add a new cartLine
			cartLine = new CartLine();
			// fetch the product
			Product product = productDAO.get(productId);
			
			cartLine.setCartId(cart.getId());
			cartLine.setProduct(product);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setProductCount(1);
			cartLine.setTotal(product.getUnitPrice());
			cartLine.setAvailable(true);
			
			cartLineDAO.add(cartLine);
			
			cart.setCartLines(cart.getCartLines() + 1);
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getBuyingPrice());
			
			cartLineDAO.updateCart(cart);
			response = "result=added";
			
		}
		else {
			// check if the cartLine has reached the maximum count
			if(cartLine.getProductCount() < 3) {
				// call the manageCartLine method to increase the count
				response = this.manageCartLine(cartLine.getId(), cartLine.getProductCount() + 1);
			}
			else {				
				response = "result=maximum";				
			}
		}
		
		return response;
	}
	
	public String validateCartLine() {
		Cart cart = this.getCart();
		List<CartLine> cartLines = cartLineDAO.list(cart.getId());
		double grandTotal = 0.0;
		int lineCount = 0;
		String response = "result=success";
		boolean changed = false;
		Product product = null;
		for(CartLine cartLine : cartLines) {					
			product = cartLine.getProduct();
			changed = false;
			// check if the product is active or not
			// if it is not active make the availability of cartLine as false
			if((!product.isActive() && product.getQuantity() == 0) && cartLine.isAvailable()) {
				cartLine.setAvailable(false);
				changed = true;
			}			
			// check if the cartLine is not available
			// check whether the product is active and has at least one quantity available
			if((product.isActive() && product.getQuantity() > 0) && !(cartLine.isAvailable())) {
					cartLine.setAvailable(true);
					changed = true;
			}
			
			// check if the buying price of product has been changed
			if(cartLine.getBuyingPrice() != product.getUnitPrice()) {
				// set the buying price to the new price
				cartLine.setBuyingPrice(product.getUnitPrice());
				// calculate and set the new total
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;				
			}
			
			// check if that much quantity of product is available or not
			if(cartLine.getProductCount() > product.getQuantity()) {
				cartLine.setProductCount(product.getQuantity());										
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;
				
			}
			
			// changes has happened
			if(changed) {				
				//update the cartLine
				cartLineDAO.update(cartLine);
				// set the result as modified
				response = "result=modified";
			}
			
			grandTotal += cartLine.getTotal();
			lineCount++;
		}
		
		cart.setCartLines(lineCount++);
		cart.setGrandTotal(grandTotal);
		cartLineDAO.updateCart(cart);

		return response;
	}		

}
