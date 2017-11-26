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

import com.fdmgroup.entities.Supplier;

public class SuppliersDAOTest {

	Supplier supplier;
	static Supplier mockSupplier;
	static SuppliersDAO suppliersDao;
	static EntityManager em;
	static TypedQuery<Supplier> mockTypedQuery;
	static EntityTransaction transaction;
	static String query;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		mockSupplier = mock(Supplier.class);
		transaction = mock(EntityTransaction.class);

		suppliersDao = new SuppliersDAO(emf);
		query = "SELECT e from Supplier e";

	}

	@Before
	public void initSupplier() {
		em = mock(EntityManager.class);
		when(suppliersDao.getEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		when(em.find(Supplier.class, 1)).thenReturn(mockSupplier);
		when(em.createQuery(query, Supplier.class)).thenReturn(mockTypedQuery);
		supplier = new Supplier("test");
	}

	@Test
	public void test_when_adding_supplier_persist_is_called() {
		suppliersDao.add(supplier);
		verify(em, times(1)).persist(supplier);
	}

	@Test
	public void test_when_getting_supplier_find_is_called() {
		suppliersDao.get(1, Supplier.class);
		verify(em, times(1)).find(Supplier.class, 1);
	}

	// @Test
	// public void test_when_listing_users_create_query_is_called(){
	// usersDao.list(User.class);
	// String query = "SELECT e from User e";
	// verify(em, times(1)).createQuery(query, User.class);
	// }

	@Test
	public void test_when_updating_supplier_merge_is_called() {
		suppliersDao.update(supplier);
		verify(em, times(1)).merge(supplier);
	}

	@Test
	public void test_when_removing_supplier_find_and_remove_are_called() {
		suppliersDao.remove(1, Supplier.class);
		verify(em, times(1)).find(Supplier.class, 1);
		verify(em, times(1)).remove(mockSupplier);
	}
}
