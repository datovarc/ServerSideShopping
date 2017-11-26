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

import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.User;

public class OrdersDAOTest {

	Order order;
	static Order mockOrder;
	static User user;
	static OrdersDAO ordersDao;
	static EntityManager em;
	static TypedQuery<Order> mockTypedQuery;
	static EntityTransaction transaction;
	static String query;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		user = mock(User.class);
		mockOrder = mock(Order.class);
		transaction = mock(EntityTransaction.class);

		ordersDao = new OrdersDAO(emf);
		query = "SELECT e from Order e";

	}

	@Before
	public void initOrder() {
		em = mock(EntityManager.class);
		when(ordersDao.getEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		when(em.find(Order.class, 1)).thenReturn(mockOrder);
		when(em.createQuery(query, Order.class)).thenReturn(mockTypedQuery);
		order = new Order(1, 1, 1, 0.0, null, null, "123 address", "Placed", user);
	}

	@Test
	public void test_when_adding_order_persist_is_called() {
		ordersDao.add(order);
		verify(em, times(1)).persist(order);
	}

	@Test
	public void test_when_getting_order_find_is_called() {
		ordersDao.get(1, Order.class);
		verify(em, times(1)).find(Order.class, 1);
	}

	// @Test
	// public void test_when_listing_users_create_query_is_called(){
	// usersDao.list(User.class);
	// String query = "SELECT e from User e";
	// verify(em, times(1)).createQuery(query, User.class);
	// }

	@Test
	public void test_when_updating_order_merge_is_called() {
		ordersDao.update(order);
		verify(em, times(1)).merge(order);
	}

	@Test
	public void test_when_removing_order_find_and_remove_are_called() {
		ordersDao.remove(1, Order.class);
		verify(em, times(1)).find(Order.class, 1);
		verify(em, times(1)).remove(mockOrder);
	}
}
