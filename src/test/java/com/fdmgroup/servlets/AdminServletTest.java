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

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.User;

public class AdminServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static AdminServlet adminServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static UsersDAO usersDao;
	static ArrayList<User> users;
	static User user;
	static HashMap<String, String> map;
	
	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		
		adminServlet = new AdminServlet();
		users = new ArrayList<User>();
		
		requestDispatcher = mock(RequestDispatcher.class);
		
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);
		usersDao = mock(UsersDAO.class);
		user = mock(User.class);
		
		users.add(user);
		map = new HashMap<>();
		map.put("isClient", "c");
		map.put("isLogistic", "l");
		map.put("isDelivery", "d");
		map.put("isAdmin", "a");
		
		when(servletConfig.getServletContext()).thenReturn(servletContext);
		
		when(servletContext.getAttribute("userdao")).thenReturn(usersDao);
		when(usersDao.list(User.class)).thenReturn(users);
		when(usersDao.get(1,User.class)).thenReturn(user);
	}
	
	@Before
	public void setup(){
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("adminusers.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("userEdited")).thenReturn("1");
		when(request.getParameterMap()).thenReturn(map);
		when(request.getParameter("isClient")).thenReturn("c");
		when(request.getParameter("isLogistic")).thenReturn("l");
		when(request.getParameter("isDelivery")).thenReturn("d");
		when(request.getParameter("isAdmin")).thenReturn("a");
	}
	
	@Test
	public void when_get_method_is_called_requestdispatcher_and_forward_are_called() {
		try {
			adminServlet.init(servletConfig);
			adminServlet.doGet(request, response);
			verify(request,times(1)).getRequestDispatcher("adminusers.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void when_post_method_is_called_requestdispatcher_and_forward_are_called() {
		try {
			adminServlet.init(servletConfig);
			adminServlet.doPost(request, response);
			verify(request,times(1)).getRequestDispatcher("adminusers.jsp");
			verify(user, times(1)).setRole("cdla");
			verify(usersDao, times(1)).update(user);
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
