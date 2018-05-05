package net.avineer.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.avineer.shoppingbackend.dao.UserDAO;
import net.avineer.shoppingbackend.dto.Address;
import net.avineer.shoppingbackend.dto.Cart;
import net.avineer.shoppingbackend.dto.User;

public class UserTestCase {

	private static AnnotationConfigApplicationContext context;
	private static UserDAO userDAO;
	private User user = null;
	private Cart cart = null;
	private Address address = null;
	
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("net.avineer.shoppingbackend");
		context.refresh();
		
		userDAO = (UserDAO) context.getBean("userDAO");
	}
	
/*
	@Test
	public void testAddUser() {
		
		user = new User() ;
		user.setFirstName("Hrithik");
		user.setLastName("Roshan");
		user.setEmail("hr@gmail.com");
		user.setContactNumber("1234512345");
		user.setRole("USER");
		//user.setEnabled(true);
		user.setPassword("12345");
		
		// add the user
		assertEquals("Failed to add the user!", true, userDAO.addUser(user));
		
		address = new Address();
		address.setAddressLineOne("101/B Jadoo Society, Krissh Nagar");
		address.setAddressLineTwo("Near Kaabil Store");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setPostalCode("400001");
		address.setBilling(true);
		
		
		// linked the address with the user
		address.setUserId(user.getId());

		
		assertEquals("Failed to add the address!", true, userDAO.addAddress(address));	
		
		if (user.getRole().equals("USER")) {
			
			cart = new Cart();
			cart.setUser(user);
			
			assertEquals("Failed to add the cart!", true, userDAO.addCart(cart));
			
			// add the shipping address
			address = new Address();
			address.setAddressLineOne("201/B Jadoo Society, Kishan Kanhaiya Nagar");
			address.setAddressLineTwo("Near Kudrat Store");
			address.setCity("Mumbai");
			address.setState("Maharashtra");
			address.setCountry("India");
			address.setPostalCode("400001");
			address.setShipping(true);
			
			address.setUserId(user.getId());;
			
			assertEquals("Failed to add the shipping address!", true, userDAO.addAddress(address));			
		}
	}
	
*/
	
/*	
	@Test
	public void testAddUser() {
		
		user = new User() ;
		user.setFirstName("Hrithik");
		user.setLastName("Roshan");
		user.setEmail("hr@gmail.com");
		user.setContactNumber("1234512345");
		user.setRole("USER");
		//user.setEnabled(true);
		user.setPassword("12345");
		

		
		if (user.getRole().equals("USER")) {
			
			cart = new Cart();
			cart.setUser(user);
			
			// attach cart with the user
			user.setCart(cart);
			
		}
		
		// add the user
		assertEquals("Failed to add the user!", true, userDAO.addUser(user));		
	}
	
*/	
	
/*	
	@Test
	public void testUpdateCart() {
		
		// fetch the user by its email
		user = userDAO.getByEmail("hr@gmail.com");
		
		// get the cart of the user
		cart = user.getCart();
		
		cart.setGrandTotal(5555);
		cart.setCartLines(2);
		
		assertEquals("Failed to update the cart!", true, userDAO.updateCart(cart));
	}
*/
	
/*	
	@Test
	public void testAddAddress() {		user = new User() ;
	
	user.setFirstName("Hrithik");
	user.setLastName("Roshan");
	user.setEmail("hr@gmail.com");
	user.setContactNumber("1234512345");
	user.setRole("USER");
	//user.setEnabled(true);
	user.setPassword("12345");	
	
	// add the user
	assertEquals("Failed to add the user!", true, userDAO.addUser(user));
	
	//------- Add billing address
	
	address = new Address();
	address.setAddressLineOne("101/B Jadoo Society, Krissh Nagar");
	address.setAddressLineTwo("Near Kaabil Store");
	address.setCity("Mumbai");
	address.setState("Maharashtra");
	address.setCountry("India");
	address.setPostalCode("400001");
	address.setBilling(true);
	
	
	// attached the user to the address
	address.setUser(user);

	
	assertEquals("Failed to add the billing address!", true, userDAO.addAddress(address));	
	
	
	//------- Add Shipping address
	
	// add the shipping address
	address = new Address();
	address.setAddressLineOne("201/B Jadoo Society, Kishan Kanhaiya Nagar");
	address.setAddressLineTwo("Near Kudrat Store");
	address.setCity("Mumbai");
	address.setState("Maharashtra");
	address.setCountry("India");
	address.setPostalCode("400001");
	address.setShipping(true);
	
	// attached the user to the address
	address.setUser(user);
	
	assertEquals("Failed to add the shipping address!", true, userDAO.addAddress(address));		
	}
*/
	
/*	
	@Test
	public void testAddAddresses() {

		user = userDAO.getByEmail("hr@gmail.com");
		
		// add the shipping address
		address = new Address();
		address.setAddressLineOne("201/B Jadoo Society, Kishan Kanhaiya Nagar");
		address.setAddressLineTwo("Near Kudrat Store");
		address.setCity("Bangalore");
		address.setState("Karanataka");
		address.setCountry("India");
		address.setPostalCode("400001");
		address.setShipping(true);
		
		// attached the user to the address
		address.setUser(user);
		
		assertEquals("Failed to add the shipping address!", true, userDAO.addAddress(address));			
	}
*/
	
/*	
	@Test
	public void testGetAddresses() {
		
		user = userDAO.getByEmail("hr@gmail.com");
		
		assertEquals("Failed to fetch the billing address!", "Mumbai", userDAO.getBillingAddress(user.getId()).getCity());
		
		assertEquals("Failed to fetch the list of shipping address!", 
				2, userDAO.listShippingAddresses(user.getId()).size());
	}
*/
	
}
