package net.avineer.onlineshopping.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import net.avineer.onlineshopping.model.RegisterModel;
import net.avineer.shoppingbackend.dao.UserDAO;
import net.avineer.shoppingbackend.dto.Address;
import net.avineer.shoppingbackend.dto.Cart;
import net.avineer.shoppingbackend.dto.User;

@Component
public class RegisterHandler {
	
	@Autowired 
	private UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public RegisterModel init() {
		return new  RegisterModel();
	}
	
	public void addUser(RegisterModel registerModel, User user) {
		registerModel.setUser(user);
	}

	public String validateUser(User user, MessageContext error) {
		String transitionValue = "success";
		
		if(!(user.getPassword().equals(user.getConfirmPassword()))) {
			
			error.addMessage(new MessageBuilder()
					.error()
					.source("confirmPassword")
					.defaultText("Password does not match the confirm password!")
					.build()
					);
			
			transitionValue = "failure";
		}
		
		// Check the uniqueness of the email
		
		if (userDAO.getByEmail(user.getEmail()) != null) {
			error.addMessage(new MessageBuilder()
					.error()
					.source("email")
					.defaultText("Email address is already used!")
					.build()
					);
			
			transitionValue = "failure";			
		}
		
		return transitionValue;
		
	}	
	
	public void addBilling(RegisterModel registerModel, Address billing) {
		registerModel.setBilling(billing);
	}	
	
	public String saveAll(RegisterModel registerModel) {
		
		String transitionValue = "success";
		
		User user = registerModel.getUser();
		if (user.getRole().equals("USER")) {
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}
		
		// Encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// Save user
		userDAO.addUser(user);
				
		Address billing = registerModel.getBilling();
		billing.setUserId(user.getId());
		billing.setBilling(true);
		// Save the address
		userDAO.addAddress(billing);	
		
		return transitionValue;
	}
	
	
}
