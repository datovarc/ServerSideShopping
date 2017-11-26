package com.fdmgroup.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.User;

/**
 * @author david.alejandro
 */
public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * A list of Users is obtained using the UsersDAO attribute from the ServletContext
	 * The list of Users are set as HttpServletRequest attribute
	 * RequestDispatcher is forwarder to 'adminusers.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("adminusers.jsp");
		req.setAttribute("users", ((UsersDAO) servletContext.getAttribute("userdao")).list(User.class));
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * userId uid is obtained from HttpServletRequest
	 * User is obtained using the UsersDAO attribute from the ServletContext and the uid
	 * User role is changed and persisted using UsersDAO
	 * A new list of users is obtained from the UsersDAO
	 * RequestDispatcher is forwarder to 'adminusers.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("adminusers.jsp");

		int uid = Integer.parseInt(req.getParameter("userEdited"));
		User user = ((UsersDAO) getServletContext().getAttribute("userdao")).get(uid, User.class);
		user.setRole(getUserRoles(req));
		((UsersDAO) getServletContext().getAttribute("userdao")).update(user);

		req.setAttribute("users", ((UsersDAO) getServletContext().getAttribute("userdao")).list(User.class));
		dispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @return new String with the roles that user has
	 */
	private String getUserRoles(HttpServletRequest req) {
		String isClient = "";
		String isDelivery = "";
		String isLogistic = "";
		String isAdmin = "";

		if (req.getParameterMap().containsKey("isClient"))
			isClient = req.getParameter("isClient");
		if (req.getParameterMap().containsKey("isDelivery"))
			isDelivery = req.getParameter("isDelivery");
		if (req.getParameterMap().containsKey("isLogistic"))
			isLogistic = req.getParameter("isLogistic");
		if (req.getParameterMap().containsKey("isAdmin"))
			isAdmin = req.getParameter("isAdmin");

		return new String(isClient + isDelivery + isLogistic + isAdmin);
	}
}
