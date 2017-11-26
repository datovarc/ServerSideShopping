package com.fdmgroup.servlets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
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

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.entities.Item;

public class ShoppingCartServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static ShoppingCartServlet shoppingCartServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static HashMap<Item, Integer> shoppingCart;
	static HashMap<String, String> map;
	static ArrayList<Item> items;
	static ItemsDAO itemDao;
	static Item item;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		shoppingCartServlet = new ShoppingCartServlet();
		map = new HashMap<>();
		map.put("itemRemoved", "1");

		itemDao = mock(ItemsDAO.class);
		item = mock(Item.class);
		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		items = new ArrayList<Item>();
		items.add(item);
		shoppingCart = new HashMap<>();
		shoppingCart.put(item, 2);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemDao);
		when(servletContext.getAttribute("items")).thenReturn(items);
		when(itemDao.get(1, Item.class)).thenReturn(item);
		when(item.getPrice()).thenReturn(10.0);
		when(session.getAttribute("shoppingCartQuantity")).thenReturn(1);
		when(session.getAttribute("shoppingCart")).thenReturn(shoppingCart);
	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("shoppingcart.jsp")).thenReturn(requestDispatcher);
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("itemRemoved")).thenReturn("1");
		when(request.getParameterMap()).thenReturn(map);
	}

	@Test
	public void when_get_method_is_called_update_shopping_cart_and_request_dispatcher_and_forward_are_called() {
		try {
			shoppingCartServlet.init(servletConfig);
			shoppingCartServlet.doGet(request, response);
			verify(request, times(1)).getRequestDispatcher("shoppingcart.jsp");
			verify(request, times(1)).getSession();
			verify(requestDispatcher, times(1)).forward(request, response);
			verify(request, times(1)).getParameter("itemRemoved");
			verify(servletContext, times(1)).getAttribute("items");
			verify(session, times(2)).getAttribute("shoppingCart");
			verify(session, times(1)).getAttribute("tempitems");
			verify(item, times(1)).getPrice();
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
