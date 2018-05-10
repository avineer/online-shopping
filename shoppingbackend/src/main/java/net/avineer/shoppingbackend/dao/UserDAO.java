package net.avineer.shoppingbackend.dao;


import java.util.List;

import net.avineer.shoppingbackend.dto.Address;
import net.avineer.shoppingbackend.dto.User;

public interface UserDAO {


	boolean addUser(User user);
	User getByEmail(String email);
	
	boolean addAddress(Address address);
	
	// Better alternative - less queries better performance without the ManyToOne annotation
	// using the user_id in the address table
	Address getBillingAddress(int userId);
	List<Address> listShippingAddresses(int userId);	
	
	//Address getBillingAddress(User user);
	//List<Address> listShippingAddresses(User user);
	

	
}
