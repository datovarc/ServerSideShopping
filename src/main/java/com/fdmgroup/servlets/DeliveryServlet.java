package com.fdmgroup.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.entities.Order;

/**
 * @author david.alejandro
 */
public class DeliveryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * A list of Orders is obtained using the OrdersDAO attribute from the ServletContext
	 * The list of Orders are set as HttpServletRequest attribute
	 * RequestDispatcher is forwarder to 'deliverycontrol.jsp'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("deliverycontrol.jsp");
		req.setAttribute("orders", ((OrdersDAO) servletContext.getAttribute("orderdao")).list(Order.class));
		requestDispatcher.forward(req, resp);
	}

	/**
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 * String status and int oid are obtained from HttpServletRequest parameters
	 * Order status is updated and persisted
	 * A new list of Orders is obtained using the OrdersDAO attribute from the ServletContext
	 * The list of Orders is set as HttpServletRequest attribute
	 * RequestDispatcher is forwarder to 'deliverycontrol.jsp'
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("deliverycontrol.jsp");

		String status = req.getParameter("orderStatus");
		int oid = Integer.parseInt(req.getParameter("orderEdited"));

		updateOrder(oid, status);

		req.setAttribute("orders", ((OrdersDAO) getServletContext().getAttribute("orderdao")).list(Order.class));

		dispatcher.forward(req, resp);
	}

	/**
	 * @param int oid - Order ID
	 * @param String status - status of current Order
	 * Obtains the Order using OrdersDAO attribute from the ServletContext and the oid
	 * Updates status of Order, if Order is Delivered, obtains current Date and saves as deliveryDate of Order
	 */
	private void updateOrder(int oid, String status) {
		Order order = ((OrdersDAO) getServletContext().getAttribute("orderdao")).get(oid, Order.class);
		order.setStatus(status);
		if (status.equals("Delivered")) {
			Date date = new Date();
			order.setDeliveryDate(date);
		}
		((OrdersDAO) getServletContext().getAttribute("orderdao")).update(order);
	}

}
