package com.fdmgroup.daos;

import javax.persistence.*;
import com.fdmgroup.entities.Item;

/**
 * 
 * @author david.alejandro
 * 
 */
public class ItemsDAO extends GenericDAO<Item> {

	/**
	 * @param EntityManagerFactory emf - emf to construct ItemsDAO
	 * emf is sent to super to be used by parent class GenericDAO
	 */
	public ItemsDAO(EntityManagerFactory emf) {
		super(emf);
	}

}
