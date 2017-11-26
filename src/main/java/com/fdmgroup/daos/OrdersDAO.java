package com.fdmgroup.daos;

import javax.persistence.*;
import com.fdmgroup.entities.Order;

/**
 * 
 * @author david.alejandro
 * 
 */
public class OrdersDAO extends GenericDAO<Order> {

	/**
	 * @param EntityManagerFactory emf - emf to construct OrdersDAO
	 * emf is sent to super to be used by parent class GenericDAO
	 */
	public OrdersDAO(EntityManagerFactory emf) {
		super(emf);
	}

}
