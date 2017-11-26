package com.fdmgroup.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.User;

/**
 * @author david.alejandro
 */
public class PaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Processes payment of User's purchase
	 * RequestDispatcher is forwarder to 'paymentcompleted.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("paymentcompleted.jsp");

		processPayment(req);

		dispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Obtains HttpSession from the HttpServletRequest
	 * Obtains uid - User Id from the HttpServletRequest parameter
	 * Obtains User using the UsersDAO obtained from the ServletContext attribute and the uid
	 * Obtains address set by the User during payment process
	 * Creates a new Order for the User's purchase and persists the Order
	 * Resets the HttpSession's Shopping Cart
	 */
	private void processPayment(HttpServletRequest req) {
		double total = Double.parseDouble(req.getParameter("totalPaid"));
		req.setAttribute("payment", total);

		HttpSession session = req.getSession();

		int uid = (int) (session.getAttribute("id"));
		Date date = new Date();
		String orderAddress = req.getParameter("orderAddress");

		User user = ((UsersDAO) getServletContext().getAttribute("userdao")).get(uid, User.class);

		@SuppressWarnings("unchecked")
		HashMap<Item, Integer> cartItems = (HashMap<Item, Integer>) session.getAttribute("shoppingCart");

		for (Item item : cartItems.keySet()) {
			Order order = new Order(uid, item.getId(), cartItems.get(item), item.getPrice() * cartItems.get(item), date,
					null, orderAddress, "Pending", user);
			((OrdersDAO) getServletContext().getAttribute("orderdao")).add(order);
		}

		session.setAttribute("shoppingCartQuantity", 0);
		session.setAttribute("shoppingCart", new HashMap<Item, Integer>());
		session.setAttribute("tempitems", new ArrayList<Item>());
	}

}
