package com.fdmgroup.servlets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;

public class RegisterServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static RegisterServlet registerServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static ArrayList<Item> items;
	static Item item;
	static UsersDAO userDao;
	static ItemsDAO itemDao;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		registerServlet = new RegisterServlet();

		items = new ArrayList<Item>();

		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);
		item = mock(Item.class);
		userDao = mock(UsersDAO.class);
		itemDao = mock(ItemsDAO.class);

		items.add(item);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("userdao")).thenReturn(userDao);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemDao);
		when(itemDao.list(Item.class)).thenReturn(items);
	}

	@Test
	public void when_get_method_is_called_request_dispatcher_and_forward_are_called() {
		try {
			request = mock(HttpServletRequest.class);
			when(request.getRequestDispatcher("registerform.jsp")).thenReturn(requestDispatcher);
			registerServlet.init(servletConfig);
			registerServlet.doGet(request, response);
			verify(request, times(1)).getRequestDispatcher("registerform.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	// private void registerUser(HttpServletRequest req) {
	// if(req.getParameter("regPassword").equals(req.getParameter("regConfirmPassword"))){
	// User user = factory.createUser(req);
	// ((UsersDAO)getServletContext().getAttribute("userdao")).add(user);
	//
	// HttpSession session = req.getSession();
	// session.setAttribute("username", user.getName());
	// session.setAttribute("password", user.getPassword());
	// session.setAttribute("role", user.getRole());
	// session.setAttribute("id", user.getId());
	// session.setAttribute("shoppingCartQuantity", 0);
	// session.setAttribute("shoppingCart", new HashMap<Item, Integer>());
	// session.setAttribute("tempitems", new ArrayList<Item>());
	// getServletContext().setAttribute("items",
	// ((ItemsDAO)getServletContext().getAttribute("itemdao")).list(Item.class));
	// }
	// }

	@Test
	public void when_post_method_is_called_request_dispatcher_and_forward_are_called() {
		request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("regPassword")).thenReturn("test");
		when(request.getParameter("regConfirmPassword")).thenReturn("test");
		when(request.getParameter("regName")).thenReturn("test");
		when(request.getParameter("regUsername")).thenReturn("test");
		when(request.getParameter("regEmail")).thenReturn("test");
		when(request.getParameter("regPhone")).thenReturn("test");
		when(request.getParameter("regAddress")).thenReturn("test");

		try {
			registerServlet.init(servletConfig);
			registerServlet.doPost(request, response);
			verify(request, times(1)).getRequestDispatcher("index.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
