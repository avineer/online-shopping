package net.avineer.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.avineer.shoppingbackend.dao.CategoryDAO;
import net.avineer.shoppingbackend.dto.Category;

public class CategoryTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	private static CategoryDAO  categoryDAO;
	
	private Category category;
	
	@BeforeClass
	public static void init () {
		
		context = new AnnotationConfigApplicationContext();
		
		context.scan("net.avineer.shoppingbackend");
		context.refresh();		
		categoryDAO = (CategoryDAO)context.getBean("categoryDAO");
	}
/*
	@Test
	public void testAddCategory() {
		
		category = new Category();
		
		category.setName("Television");
		category.setDescription("This is a television description");
		category.setImageURL("CAT_1.png");
		
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));
		
	}
*/	
	
/*	
	@Test
	public void testGetCategory() {
		
		category = categoryDAO.get(4);
		assertEquals("Successfully fetched a  single category from the table!", "Television", category.getName());
	
	}
*/
	
/*	
	@Test
	public void testUpdateCategory() {
		
		category = categoryDAO.get(4);
		category.setName("TV");
				
		assertEquals("Successfully updated a  single category from the table!", true, categoryDAO.update(category));	
	}	
*/

/*	
	@Test
	public void testDeleteCategory() {
		
		category = categoryDAO.get(4);
		assertEquals("Successfully deleted a single category from the table!", true, categoryDAO.delete(category));
	
	}
*/
	
	/*
	@Test
	public void testListCategory() {
		
		assertEquals("Successfully fetched a list of categoriers from the table!", 3, categoryDAO.list().size());
	
	}
	
	*/
	
	@Test
	public void testCRUDCategory() {
		
		category = new Category();
		
		category.setName("Laptop");
		category.setDescription("This is a description for laptop");
		category.setImageURL("CAT_1.png");
		
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));

		category = new Category();
		
		category.setName("Television");
		category.setDescription("This is a description for television");
		category.setImageURL("CAT_2.png");
		
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));

		// fetch and update the category
		category = categoryDAO.get(2);
		category.setName("TV");
				
		assertEquals("Successfully updated a  single category from the table!", true, categoryDAO.update(category));			

		// Delete the category
		assertEquals("Successfully deleted a single category from the table!", true, categoryDAO.delete(category));
		
		// fetching the list of active categories
		assertEquals("Successfully fetched a list of categoriers from the table!", 1, categoryDAO.list().size());
		
	
	}
}
