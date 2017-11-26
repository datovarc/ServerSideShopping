package com.fdmgroup.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.User;

/**
 * @author david.alejandro
 */
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = -5133081845658807901L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * If the HttpServletRequest contains itemAdded parameter, then update ShoppingCart
	 * RequestDispatcher is forwarder to 'index.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");

		if (req.getParameterMap().containsKey("itemAdded")) {
			updateShoppingCart(req);
		}
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Validates User trying to login
	 * Updates the list of Items set in the ServletContext
	 * RequestDispatcher is forwarder to 'index.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");

		validateUser(req);

		getServletContext().setAttribute("items",
				((ItemsDAO) getServletContext().getAttribute("itemdao")).list(Item.class));

		dispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * Gets int iid - Item ID from the HttpServletRequest
	 * Obtains Item using the ItemsDAO attribute from the ServletContext and the iid
	 * Obtains HttpSession from the HttpServletRequest
	 * Obtains the CartItems from the current HttpSession
	 * Updates the CartItems HashMap adding Item accordingly
	 * Updates the Item inventory
	 * Updates the list of Items set in the ServletContext for display
	 */
	private void updateShoppingCart(HttpServletRequest req) {
		HttpSession session = req.getSession();
		int iid = Integer.parseInt(req.getParameter("itemAdded"));
		Item item = ((ItemsDAO) getServletContext().getAttribute("itemdao")).get(iid, Item.class);

		@SuppressWarnings("unchecked")
		HashMap<Item, Integer> cartItems = (HashMap<Item, Integer>) session.getAttribute("shoppingCart");

		@SuppressWarnings("unchecked")
		ArrayList<Item> tempItems = (ArrayList<Item>) session.getAttribute("tempitems");

		if (cartItems.containsKey(item)) {
			int currQuantity = cartItems.get(item);
			cartItems.put(item, currQuantity + 1);
		} else {
			cartItems.put(item, 1);
			tempItems.add(item);
		}

		@SuppressWarnings("unchecked")
		ArrayList<Item> items = (ArrayList<Item>) getServletContext().getAttribute("items");

		for (Item i : items) {
			if (i.getId() == item.getId()) {
				i.setInventory(i.getInventory() - 1);
			}
		}

		getServletContext().setAttribute("items", items);
		int currQuantity = (int) session.getAttribute("shoppingCartQuantity");
		session.setAttribute("shoppingCartQuantity", currQuantity + 1);
	}

	/**
	 * @param HttpServletRequest req
	 * Obtains List of Users using the UsersDAO attribute from the ServletContext
	 * Obtains the username and password from the HttpServletRequest
	 * Compares username and password pair with a matching pair in the database
	 * If match is found, creates a HttpSession and register user information, together with Shopping Cart info
	 */
	private void validateUser(HttpServletRequest req) {
		List<User> users = ((UsersDAO) getServletContext().getAttribute("userdao")).list(User.class);

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				HttpSession session = req.getSession();
				session.setAttribute("username", user.getName());
				session.setAttribute("password", user.getPassword());
				session.setAttribute("role", user.getRole());
				session.setAttribute("id", user.getId());
				session.setAttribute("shoppingCartQuantity", 0);
				session.setAttribute("shoppingCart", new HashMap<Item, Integer>());
				session.setAttribute("tempitems", new ArrayList<Item>());
			}
		}
	}

}
