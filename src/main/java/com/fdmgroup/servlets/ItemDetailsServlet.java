package com.fdmgroup.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.entities.Item;

/**
 * @author david.alejandro
 */
public class ItemDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = -5133081845658807901L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Obtains iid - Item Id from the HttpServletRequest parameter
	 * Obtains Item using the ItemsDAO obtained from the ServletContext attribute and the iid
	 * Sets Item as an HttpRequestAttribute to be detailed
	 * RequestDispatcher is forwarder to 'itemdetails.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("itemdetails.jsp");
		int iid = Integer.parseInt(req.getParameter("iid"));
		req.setAttribute("item", ((ItemsDAO) servletContext.getAttribute("itemdao")).get(iid, Item.class));
		requestDispatcher.forward(req, resp);
	}

}
