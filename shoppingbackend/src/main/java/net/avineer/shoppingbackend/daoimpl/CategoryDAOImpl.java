package net.avineer.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.avineer.shoppingbackend.dao.CategoryDAO;
import net.avineer.shoppingbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<Category> list() {
		String selectActiveCategory = "FROM Category WHERE active = :active";
		Query<Category> query = sessionFactory.getCurrentSession().createQuery(selectActiveCategory);
		query.setParameter("active", true);
		return query.getResultList();
	}
	
    // Getting a single category by primary key
	@Override
	public Category get(int id) {
		return sessionFactory.getCurrentSession().get(Category.class, Integer.valueOf(id));
	}

	@Override
	public boolean add(Category category) {
		
		try {
			// Add category to the database table
			sessionFactory.getCurrentSession().persist(category);
			return true;			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}	
	}

	@Override
	public boolean update(Category category) {
		try {
			// Update category to the database table
			sessionFactory.getCurrentSession().update(category);
			return true;			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}	
	}

	@Override
	public boolean delete(Category category) {
		try {
			// Delete (deactivate) category to the database table
			category.setActive(false);
			sessionFactory.getCurrentSession().update(category);
			return true;			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}	
	}
	

}
