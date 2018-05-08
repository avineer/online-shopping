package net.avineer.onlineshopping.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.avineer.onlineshopping.model.UserModel;
import net.avineer.shoppingbackend.dao.UserDAO;
import net.avineer.shoppingbackend.dto.User;

@ControllerAdvice
public class GlobalController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserDAO userDAO;
	
	private UserModel userModel = null;

	@ModelAttribute("userModel")
	public UserModel getUserModel() {
		
		if(session.getAttribute("userModel") == null) {
			// add the user model
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			User user = userDAO.getByEmail(authentication.getName());
			if (user != null) {
				// Create a new UserModel to pass the user details
				userModel = new UserModel();
				userModel.setId(user.getId());
				userModel.setEmail(user.getEmail());
				userModel.setRole(user.getRole());
				userModel.setFullName(user.getFirstName()+" " +user.getLastName());
				if (userModel.getRole().equals("USER")) {
					// set the cart only if the user is a buyer
					userModel.setCart(user.getCart());
				}
				// set the useModel in the session
				session.setAttribute("userModel", userModel);
				
				return userModel;
				
			}
		}
		
		return (UserModel) session.getAttribute("userModel");
		
	}
}
