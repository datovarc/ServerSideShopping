package com.fdmgroup.daos;

import javax.persistence.EntityManagerFactory;
import com.fdmgroup.entities.User;

/**
 * 
 * @author david.alejandro
 * 
 */
public class UsersDAO extends GenericDAO<User> {

	/**
	 * @param EntityManagerFactory emf - emf to construct UsersDAO
	 * emf is sent to super to be used by parent class GenericDAO
	 */
	public UsersDAO(EntityManagerFactory emf) {
		super(emf);
	}

}