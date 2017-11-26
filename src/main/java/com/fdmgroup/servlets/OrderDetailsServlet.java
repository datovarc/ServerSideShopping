package com.fdmgroup.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.User;

/**
 * @author david.alejandro
 */
public class OrderDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = -5133081845658807901L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * Sets the Order info to be detailed
	 * RequestDispatcher is forwarder to 'orderdetails.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("orderdetails.jsp");
		setOrderInfo(req);
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * Obtains oid - Order Id from the HttpServletRequest parameter
	 * Obtains Order using the OrdersDAO obtained from the ServletContext attribute and the oid
	 * Sets the Order as an attribute to HttpServletRequest
	 * Obtains the uid - User ID and iid - Item ID from the Order and sets them as HttpServletRequest attribute
	 */
	private void setOrderInfo(HttpServletRequest req) {
		int oid = Integer.parseInt(req.getParameter("oid"));
		Order order = ((OrdersDAO) getServletContext().getAttribute("orderdao")).get(oid, Order.class);
		req.setAttribute("order", order);
		int uid = order.getUserId();
		int iid = order.getItemId();
		req.setAttribute("user", ((UsersDAO) getServletContext().getAttribute("userdao")).get(uid, User.class));
		req.setAttribute("item", ((ItemsDAO) getServletContext().getAttribute("itemdao")).get(iid, Item.class));
	}

}
