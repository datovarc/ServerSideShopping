package com.fdmgroup.servlets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.User;

public class OrderDetailsServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static OrderDetailsServlet orderDetailsServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static OrdersDAO orderDao;
	static UsersDAO userDao;
	static ItemsDAO itemDao;
	static Order order;
	static User user;
	static Item item;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		orderDetailsServlet = new OrderDetailsServlet();

		orderDao = mock(OrdersDAO.class);
		order = mock(Order.class);
		userDao = mock(UsersDAO.class);
		user = mock(User.class);
		itemDao = mock(ItemsDAO.class);
		item = mock(Item.class);

		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("orderdao")).thenReturn(orderDao);
		when(servletContext.getAttribute("userdao")).thenReturn(userDao);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemDao);
		when(orderDao.get(1, Order.class)).thenReturn(order);
		when(userDao.get(1, User.class)).thenReturn(user);
		when(itemDao.get(1, Item.class)).thenReturn(item);
		when(order.getItemId()).thenReturn(1);
		when(order.getUserId()).thenReturn(1);
	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("orderdetails.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("oid")).thenReturn("1");
	}

	@Test
	public void when_get_method_is_called_order_attributes_are_set_and_request_dispatcher_and_forward_are_called() {
		try {
			orderDetailsServlet.init(servletConfig);
			orderDetailsServlet.doGet(request, response);
			verify(request, times(1)).getRequestDispatcher("orderdetails.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
