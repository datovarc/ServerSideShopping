package com.fdmgroup.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.entities.Item;

/**
 * @author david.alejandro
 */
public class LogisticServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * RequestDispatcher is forwarder to 'itemlogistics.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("itemlogistics.jsp");
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * RequestDispatcher is forwarder to 'itemlogistics.jsp'
	 * Item's inventory is updated
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("itemlogistics.jsp");

		updateItem(req);

		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * Obtains iid - Item Id and quantity from the HttpServletRequest parameter
	 * Obtains Item using the ItemsDAO obtained from the ServletContext attribute and the iid
	 * Updates the Item's inventory and persists
	 * Updates Items list in the ServletContext for display
	 */
	private void updateItem(HttpServletRequest req) {
		int iid = Integer.parseInt(req.getParameter("itemOrdered"));
		int quantity = Integer.parseInt(req.getParameter("itemQuantity"));
		Item item = ((ItemsDAO) getServletContext().getAttribute("itemdao")).get(iid, Item.class);
		item.setInventory(item.getInventory() + quantity);
		((ItemsDAO) getServletContext().getAttribute("itemdao")).update(item);
		getServletContext().setAttribute("items",
				((ItemsDAO) getServletContext().getAttribute("itemdao")).list(Item.class));
	}

}
