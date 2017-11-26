package com.fdmgroup.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.entities.Item;

/**
 * @author david.alejandro
 */
public class ShoppingCartServlet extends HttpServlet {

	private static final long serialVersionUID = -5133081845658807901L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Obtains HttpSession from the HttpServletRequest
	 * Removes item from the Shopping Cart
	 * Updates HttpServletRequest attribute 'total'
	 * RequestDispatcher is forwarder to 'shoppingcart.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("shoppingcart.jsp");
		HttpSession session = req.getSession();
		removeFromShoppingCart(req, session);
		req.setAttribute("total", getTotal(req, session));
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * Obtains iid - Item Id from the HttpServletRequest parameter
	 * Obtains Item using the ItemsDAO obtained from the ServletContext attribute and the iid
	 * Obtains a list of Items using the ItemsDAO, and updates each item's inventory depending on the Items being pushed out of the Shopping Cart
	 * Removes Items from the Shopping Cart HashMap and updates the HttpSession Attributes
	 */
	private void removeFromShoppingCart(HttpServletRequest req, HttpSession session) {
		if (req.getParameterMap().containsKey("itemRemoved")) {

			int iid = Integer.parseInt(req.getParameter("itemRemoved"));
			Item item = ((ItemsDAO) getServletContext().getAttribute("itemdao")).get(iid, Item.class);

			@SuppressWarnings("unchecked")
			ArrayList<Item> items = (ArrayList<Item>) getServletContext().getAttribute("items");

			for (Item i : items) {
				if (i.getId() == item.getId()) {
					i.setInventory(i.getInventory() + 1);
				}
			}

			getServletContext().setAttribute("items", items);

			@SuppressWarnings("unchecked")
			HashMap<Item, Integer> cartItems = (HashMap<Item, Integer>) session.getAttribute("shoppingCart");

			@SuppressWarnings("unchecked")
			ArrayList<Item> tempItems = (ArrayList<Item>) session.getAttribute("tempitems");

			if (cartItems.containsKey(item)) {
				int currQuantity = cartItems.get(item);
				if (currQuantity > 1)
					cartItems.put(item, currQuantity - 1);
				else {
					cartItems.remove(item);
					tempItems.remove(item);
				}
			}

			session.setAttribute("shoppingCart", cartItems);
			session.setAttribute("tempitems", tempItems);
			session.setAttribute("shoppingCartQuantity", (int) session.getAttribute("shoppingCartQuantity") - 1);
		}
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @return double total - total price to be paid
	 * Obtains the Cart Items from the HttpSession
	 * Calculates Total to be paid looping through all the HashMap Keys
	 */
	private double getTotal(HttpServletRequest req, HttpSession session) {

		@SuppressWarnings("unchecked")
		HashMap<Item, Integer> cartItems = (HashMap<Item, Integer>) session.getAttribute("shoppingCart");
		double total = 0.0;
		for (Item key : cartItems.keySet()) {
			int qty = cartItems.get(key);
			double subtotal = key.getPrice() * qty;
			total += subtotal;
		}
		return total;
	}

}
