package com.fdmgroup.daos;

import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.Supplier;

public class ItemsDAOTest {

	Item item;
	static Item mockItem;
	static ItemsDAO itemsDao;
	static EntityManager em;
	static TypedQuery<Item> mockTypedQuery;
	static Supplier supplier;
	static EntityTransaction transaction;
	static String query;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		EntityManagerFactory emf = mock(EntityManagerFactory.class);

		mockItem = mock(Item.class);
		supplier = mock(Supplier.class);
		transaction = mock(EntityTransaction.class);

		itemsDao = new ItemsDAO(emf);
		query = "SELECT e from Item e";

	}

	@Before
	public void initItem() {
		em = mock(EntityManager.class);
		when(itemsDao.getEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		when(em.find(Item.class, 1)).thenReturn(mockItem);
		when(em.createQuery(query, Item.class)).thenReturn(mockTypedQuery);
		item = new Item("test", "test", 0.0, 0.0, 100, supplier);
	}

	@Test
	public void test_when_adding_item_persist_is_called() {
		itemsDao.add(item);
		verify(em, times(1)).persist(item);
	}

	@Test
	public void test_when_getting_item_find_is_called() {
		itemsDao.get(1, Item.class);
		verify(em, times(1)).find(Item.class, 1);
	}

	// @Test
	// public void test_when_listing_users_create_query_is_called(){
	// usersDao.list(User.class);
	// String query = "SELECT e from User e";
	// verify(em, times(1)).createQuery(query, User.class);
	// }

	@Test
	public void test_when_updating_item_merge_is_called() {
		itemsDao.update(item);
		verify(em, times(1)).merge(item);
	}

	@Test
	public void test_when_removing_item_find_and_remove_are_called() {
		itemsDao.remove(1, Item.class);
		verify(em, times(1)).find(Item.class, 1);
		verify(em, times(1)).remove(mockItem);
	}
}
