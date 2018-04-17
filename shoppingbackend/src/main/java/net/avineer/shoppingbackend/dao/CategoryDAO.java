package net.avineer.shoppingbackend.dao;

import java.util.List;

import net.avineer.shoppingbackend.dta.Category;

public interface CategoryDAO {

	List<Category> list();
	
	Category get(int id);
}
