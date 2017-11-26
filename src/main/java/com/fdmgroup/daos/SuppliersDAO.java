package com.fdmgroup.daos;

import javax.persistence.EntityManagerFactory;
import com.fdmgroup.entities.Supplier;

/**
 * 
 * @author david.alejandro
 * 
 */
public class SuppliersDAO extends GenericDAO<Supplier> {

	/**
	 * @param EntityManagerFactory emf - emf to construct SuppliersDAO
	 * emf is sent to super to be used by parent class GenericDAO
	 */
	public SuppliersDAO(EntityManagerFactory emf) {
		super(emf);
	}

}
