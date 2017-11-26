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
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.User;
import com.fdmgroup.factories.UserFactory;

/**
 * @author david.alejandro
 */
public class RegisterServlet extends HttpServlet {

	private UserFactory factory;

	private static final long serialVersionUID = -5133081845658807901L;

	public RegisterServlet() {
		this(new UserFactory());
	}

	public RegisterServlet(UserFactory factory) {
		this.factory = factory;
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * RequestDispatcher is forwarder to 'registerform.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("registerform.jsp");
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Registers User
	 * RequestDispatcher is forwarder to 'index.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
		registerUser(req);
		dispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Verifies that the password and password confirmation are the same
	 * If matches, Calls the UserFactory to create a new User using the HttpServletRequest parameters
	 * Obtains an HttpSession from the HttpServletRequest
	 * Sets User information as Attributes for the HttpSession, together with the ShoppingCart information
	 * Updates Item's List to be displayed
	 */
	private void registerUser(HttpServletRequest req) {
		if (req.getParameter("regPassword").equals(req.getParameter("regConfirmPassword"))) {
			User user = factory.createUser(req);
			((UsersDAO) getServletContext().getAttribute("userdao")).add(user);

			HttpSession session = req.getSession();
			session.setAttribute("username", user.getName());
			session.setAttribute("password", user.getPassword());
			session.setAttribute("role", user.getRole());
			session.setAttribute("id", user.getId());
			session.setAttribute("shoppingCartQuantity", 0);
			session.setAttribute("shoppingCart", new HashMap<Item, Integer>());
			session.setAttribute("tempitems", new ArrayList<Item>());
			getServletContext().setAttribute("items",
					((ItemsDAO) getServletContext().getAttribute("itemdao")).list(Item.class));
		}
	}

}
