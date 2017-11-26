package com.fdmgroup.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.User;

/**
 * @author david.alejandro
 */
public class UserDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Sets User's information to be detailed
	 * RequestDispatcher is forwarder to 'userdetails.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("userdetails.jsp");
		setUserInfo(req);
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Updates User Info
	 * RequestDispatcher is forwarder to 'userdetails.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("userdetails.jsp");
		updateUserInfo(req);
		dispatcher.forward(req, resp);

	}

	/**
	 * @param HttpServletRequest req
	 * Obtains uid - User Id from the HttpServletRequest parameter
	 * Obtains User using the UsersDAO obtained from the ServletContext attribute and the uid
	 * Updates User information and persists
	 * Obtains the HttpSession from HttpServletRequest
	 * Updates the HttpSession Attributes for the User
	 * Sets this Updated User as the attribute to be detailed
	 */
	private void updateUserInfo(HttpServletRequest req) {
		int uid = Integer.parseInt(req.getParameter("userEdited"));
		User user = ((UsersDAO) getServletContext().getAttribute("userdao")).get(uid, User.class);
		user.setAddress(req.getParameter("userAddress"));
		user.setEmail(req.getParameter("userEmail"));
		user.setName(req.getParameter("name"));
		user.setPhone(req.getParameter("userPhone"));
		user.setUsername(req.getParameter("userUsername"));

		String oldPass = req.getParameter("oldPass");
		String newPass = req.getParameter("newPass");
		String confirmNewPass = req.getParameter("confirmNewPass");
		if (oldPass.equals(user.getPassword()) && newPass.equals(confirmNewPass)) {
			user.setPassword(newPass);
		}
		((UsersDAO) getServletContext().getAttribute("userdao")).update(user);

		HttpSession session = req.getSession();
		session.setAttribute("username", user.getName());
		session.setAttribute("password", user.getPassword());
		session.setAttribute("role", user.getRole());
		session.setAttribute("id", user.getId());

		req.setAttribute("userdetailed", user);
		req.setAttribute("delivery", false);
	}

	/**
	 * @param HttpServletRequest req
	 * Obtains the HttpSession from HttpServletRequest
	 * If Current user is being detailed, set attribute to display edit options
	 * Else don't display edit options
	 * Set User as the one to be detailed
	 */
	private void setUserInfo(HttpServletRequest req) {
		HttpSession session = req.getSession();
		int id = -1;
		if (req.getParameterMap().containsKey("uid")) {
			id = Integer.parseInt(req.getParameter("uid"));
			req.setAttribute("delivery", true);
		} else {
			id = (int) session.getAttribute("id");
			req.setAttribute("delivery", false);
		}
		req.setAttribute("userdetailed", ((UsersDAO) getServletContext().getAttribute("userdao")).get(id, User.class));
	}

}
