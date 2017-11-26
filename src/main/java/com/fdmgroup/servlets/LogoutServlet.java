package com.fdmgroup.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author david.alejandro
 */
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -5133081845658807901L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Obtains current HttpSession from the HttpServletRequest
	 * Invalidates the HttpSession
	 * RequestDispatcher is forwarder to 'index.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
		HttpSession session = req.getSession();
		session.invalidate();
		requestDispatcher.forward(req, resp);
	}
}