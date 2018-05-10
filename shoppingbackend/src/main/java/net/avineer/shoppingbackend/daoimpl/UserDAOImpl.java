package net.avineer.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.avineer.shoppingbackend.dao.UserDAO;
import net.avineer.shoppingbackend.dto.Address;
import net.avineer.shoppingbackend.dto.User;


@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public boolean addUser(User user) {
		try {			
			sessionFactory.getCurrentSession().persist(user);			
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}

	@Override
	public boolean addAddress(Address address) {
		try {			
			sessionFactory.getCurrentSession().persist(address);			
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	

	@Override
	public User getByEmail(String email) {
		String selectQuery = "FROM User WHERE email = :email";
		//Query query = sessionFactory.getCurrentSession().createQuery(selectQuery);
		
		try {
			
			return sessionFactory.getCurrentSession().createQuery(selectQuery, User.class)
					.setParameter("email", email).getSingleResult();
		}
		catch (Exception ex) {
			//ex.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public Address getBillingAddress(int userId) {
		
		String selectQuery = "FROM Address WHERE userId = :userId AND billing = :billing";
		
		try {
			return sessionFactory.getCurrentSession().createQuery(selectQuery, Address.class)
					.setParameter("userId", userId)
					.setParameter("billing", true)
					.getSingleResult();		
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
	}

	@Override
	public List<Address> listShippingAddresses(int userId) {
		String selectQuery = "FROM Address WHERE userId = :userId AND shipping = :shipping";
		try {
			return sessionFactory.getCurrentSession().createQuery(selectQuery, Address.class)
					.setParameter("userId", userId)
					.setParameter("shipping", true)
					.getResultList();		
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}	
	}
	

}
