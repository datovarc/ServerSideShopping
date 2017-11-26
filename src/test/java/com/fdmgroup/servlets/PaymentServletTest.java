package com.fdmgroup.servlets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;

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

import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.User;

public class PaymentServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static PaymentServlet paymentServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static UsersDAO userDao;
	static OrdersDAO orderDao;
	static User user;
	static Item item;
	static HashMap<Item, Integer> shoppingCart;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		paymentServlet = new PaymentServlet();
		shoppingCart = new HashMap<>();

		orderDao = mock(OrdersDAO.class);
		userDao = mock(UsersDAO.class);
		user = mock(User.class);
		item = mock(Item.class);
		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		shoppingCart.put(item, 2);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("userdao")).thenReturn(userDao);
		when(userDao.get(1, User.class)).thenReturn(user);
		when(session.getAttribute("id")).thenReturn(1);
		when(session.getAttribute("shoppingCart")).thenReturn(shoppingCart);
		when(item.getId()).thenReturn(1);
		when(item.getPrice()).thenReturn(30.0);
		when(servletContext.getAttribute("orderdao")).thenReturn(orderDao);
	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("paymentcompleted.jsp")).thenReturn(requestDispatcher);
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("totalPaid")).thenReturn("1");
		when(request.getParameter("orderAddress")).thenReturn("123 test");
	}

	@Test
	public void when_post_method_is_called_request_dispatcher_and_forward_are_called() {
		try {
			paymentServlet.init(servletConfig);
			paymentServlet.doPost(request, response);
			verify(request, times(1)).getRequestDispatcher("paymentcompleted.jsp");
			verify(request, times(1)).getSession();
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
