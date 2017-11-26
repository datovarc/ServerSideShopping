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
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.User;

public class IndexServletTest {

	static IndexServlet indexServlet;
	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static ItemsDAO itemsDao;
	static ArrayList<Item> items;
	static UsersDAO usersDao;
	static ArrayList<User> users;
	static Item item;
	static User user;
	static HashMap<String, String> map;
	static HashMap<Item, Integer> shoppingCart;
	static HttpSession session;
	
	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		indexServlet = new IndexServlet();
		
		items = new ArrayList<Item>();
		users = new ArrayList<User>();
		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);
		itemsDao = mock(ItemsDAO.class);
		usersDao = mock(UsersDAO.class);
		item = mock(Item.class);
		user = mock(User.class);
		
		items.add(item);
		users.add(user);
		map = new HashMap<>();
		map.put("itemAdded", "1");
		shoppingCart = new HashMap<>();
		shoppingCart.put(item, 2);
				
		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemsDao);
		when(servletContext.getAttribute("userdao")).thenReturn(usersDao);
		when(servletContext.getAttribute("items")).thenReturn(items);
		when(itemsDao.list(Item.class)).thenReturn(items);
		when(usersDao.list(User.class)).thenReturn(users);
		when(itemsDao.get(1,Item.class)).thenReturn(item);
		when(user.getUsername()).thenReturn("test");
		when(user.getPassword()).thenReturn("test");
		
	}
	
	@Before
	public void setup(){
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
		when(request.getParameterMap()).thenReturn(map);
		when(request.getParameter("itemAdded")).thenReturn("1");
		when(request.getParameter("username")).thenReturn("test");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("shoppingCart")).thenReturn(shoppingCart);		
		when(session.getAttribute("shoppingCartQuantity")).thenReturn(2);
		
	}
	
	
	@Test
	public void when_get_method_is_called_request_dispatcher_parameter_map_and_forward_are_called_shopping_cart_contains_item() {
		try {
			indexServlet.init(servletConfig);
			indexServlet.doGet(request, response);
			verify(request,times(1)).getRequestDispatcher("index.jsp");
			verify(request, times(1)).getParameterMap();
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void when_post_method_is_called_user_is_validated_and_request_dispatcher_and_forward_are_called() {
		try {
			indexServlet.init(servletConfig);
			indexServlet.doPost(request, response);
			verify(request,times(1)).getRequestDispatcher("index.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
			verify(request, times(1)).getParameter("username");
			verify(request, times(1)).getParameter("password");
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
