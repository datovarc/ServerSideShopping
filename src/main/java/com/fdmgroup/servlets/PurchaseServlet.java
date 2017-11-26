package com.fdmgroup.servlets;

import java.io.IOException;
import java.util.ArrayList;

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
public class PurchaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Processes User's purchase
	 * RequestDispatcher is forwarder to 'purchase.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("purchase.jsp");

		processPurchase(req);

		dispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Obtains a List of Items from the ServletContext
	 * Updates the Items in the list and persists
	 * Obtains address of User to be set as default for Order processing, unless later changes by User
	 */
	private void processPurchase(HttpServletRequest req) {
		double total = Double.parseDouble(req.getParameter("totalPaid"));

		req.setAttribute("totalPaid", total);

		@SuppressWarnings("unchecked")
		ArrayList<Item> items = (ArrayList<Item>) getServletContext().getAttribute("items");
		for (Item i : items) {
			((ItemsDAO) getServletContext().getAttribute("itemdao")).update(i);
		}
		getServletContext().setAttribute("items", items);

		HttpSession session = req.getSession();

		int uid = (int) session.getAttribute("id");
		String address = ((UsersDAO) getServletContext().getAttribute("userdao")).get(uid, User.class).getAddress();
		req.setAttribute("userAddress", address);
	}

}
