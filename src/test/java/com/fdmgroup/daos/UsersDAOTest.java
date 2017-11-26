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

import com.fdmgroup.entities.User;

public class UsersDAOTest {

	User user;
	static User mockUser;
	static UsersDAO usersDao;
	static EntityManager em;
	static TypedQuery<User> mockTypedQuery;
	static EntityTransaction transaction;
	static String query;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		EntityManagerFactory emf = mock(EntityManagerFactory.class);

		mockUser = mock(User.class);
		transaction = mock(EntityTransaction.class);

		usersDao = new UsersDAO(emf);
		query = "SELECT e from User e";
	}

	@Before
	public void initUser() {
		em = mock(EntityManager.class);
		when(usersDao.getEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		when(em.find(User.class, 1)).thenReturn(mockUser);
		when(em.createQuery(query, User.class)).thenReturn(mockTypedQuery);
		user = new User("test", "test", "test", "test@test", "12345678", "123 test", "C");
	}

	@Test
	public void test_when_adding_user_persist_is_called() {
		usersDao.add(user);
		verify(em, times(1)).persist(user);
	}

	@Test
	public void test_when_getting_user_find_is_called() {
		usersDao.get(1, User.class);
		verify(em, times(1)).find(User.class, 1);
	}

	// @Test
	// public void test_when_listing_users_create_query_is_called(){
	// usersDao.list(User.class);
	// String query = "SELECT e from User e";
	// verify(em, times(1)).createQuery(query, User.class);
	// }

	@Test
	public void test_when_updating_user_merge_is_called() {
		usersDao.update(user);
		verify(em, times(1)).merge(user);
	}

	@Test
	public void test_when_removing_user_find_and_remove_are_called() {
		usersDao.remove(1, User.class);
		verify(em, times(1)).find(User.class, 1);
		verify(em, times(1)).remove(mockUser);
	}
}
