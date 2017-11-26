package com.fdmgroup.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.daos.SuppliersDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;

/**
 * @author david.alejandro
 */
public class ApplicationLoadedListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * @param ServletContextEvent sce
	 * EntityManagerFactory is created through Persistence class
	 * DAO objects are initialized using the EntityManagerFactory
	 * ServletContext is obtained from the ServletContextEvent sce
	 * DAO objects are set as attributes into the ServletContext
	 * A list of Items is preloaded as an attribute into the ServletContext
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("retailshopping");

		UsersDAO udao = new UsersDAO(emf);
		ItemsDAO idao = new ItemsDAO(emf);
		SuppliersDAO sdao = new SuppliersDAO(emf);
		OrdersDAO odao = new OrdersDAO(emf);

		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute("userdao", udao);
		servletContext.setAttribute("itemdao", idao);
		servletContext.setAttribute("supplierdao", sdao);
		servletContext.setAttribute("orderdao", odao);
		servletContext.setAttribute("items", idao.list(Item.class));

	}

}
