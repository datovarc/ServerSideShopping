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
public class RequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Places request for Supplier
	 * RequestDispatcher is forwarder to 'request.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("request.jsp");

		placeSupplierRequest(req);

		dispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * Obtains iid - Item Id and quantity from the HttpServletRequest parameter
	 * Obtains Item using the ItemsDAO obtained from the ServletContext attribute and the iid
	 * Calculates the total using the Item Cost and the quantity
	 * Sets values as attributes in the HttpServletRequest
	 */
	private void placeSupplierRequest(HttpServletRequest req) {
		int iid = Integer.parseInt(req.getParameter("itemOrdered"));
		Item item = ((ItemsDAO) getServletContext().getAttribute("itemdao")).get(iid, Item.class);
		int quantity = Integer.parseInt(req.getParameter("itemQuantity")) - item.getInventory();
		double totalCost = item.getCost() * quantity;

		req.setAttribute("totalCost", totalCost);
		req.setAttribute("item", item);
		req.setAttribute("itemQuantity", quantity);
	}
}
