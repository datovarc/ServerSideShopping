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

import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.User;

public class UserDetailsServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static UserDetailsServlet userDetailsServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static HashMap<String, String> map;
	static UsersDAO userDao;
	static User user;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		userDetailsServlet = new UserDetailsServlet();

		map = new HashMap<>();
		map.put("uid", "1");

		userDao = mock(UsersDAO.class);
		user = mock(User.class);
		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("userdao")).thenReturn(userDao);
		when(userDao.get(1, User.class)).thenReturn(user);
		when(session.getAttribute("id")).thenReturn(1);
		when(user.getPassword()).thenReturn("test");
		when(user.getName()).thenReturn("test");
		when(user.getRole()).thenReturn("t");
		when(user.getId()).thenReturn(1);

	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("userdetails.jsp")).thenReturn(requestDispatcher);
		when(request.getSession()).thenReturn(session);
		when(request.getParameterMap()).thenReturn(map);
		when(request.getParameter("uid")).thenReturn("1");
		when(request.getParameter("userEdited")).thenReturn("1");
		when(request.getParameter("userAddress")).thenReturn("test");
		when(request.getParameter("userEmail")).thenReturn("test");
		when(request.getParameter("name")).thenReturn("test");
		when(request.getParameter("userPhone")).thenReturn("test");
		when(request.getParameter("userUsername")).thenReturn("test");
		when(request.getParameter("oldPass")).thenReturn("test");
		when(request.getParameter("newPass")).thenReturn("test");
		when(request.getParameter("confirmNewPass")).thenReturn("test");
	}

	@Test
	public void when_get_method_is_called_user_info_is_requested_and_request_dispatcher_and_forward_are_called() {
		try {
			userDetailsServlet.init(servletConfig);
			userDetailsServlet.doGet(request, response);
			verify(request, times(1)).getRequestDispatcher("userdetails.jsp");
			verify(request, times(1)).getSession();
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void when_post_method_is_called_user_info_is_updated_and_request_dispatcher_and_forward_are_called() {
		try {
			userDetailsServlet.init(servletConfig);
			userDetailsServlet.doPost(request, response);
			verify(request, times(1)).getRequestDispatcher("userdetails.jsp");
			verify(request, times(1)).getSession();
			verify(requestDispatcher, times(1)).forward(request, response);
			verify(request, times(1)).getParameter("userAddress");
			verify(request, times(1)).getParameter("userEmail");
			verify(request, times(1)).getParameter("name");
			verify(request, times(1)).getParameter("userPhone");
			verify(request, times(1)).getParameter("userUsername");
			verify(request, times(1)).getParameter("oldPass");
			verify(request, times(1)).getParameter("newPass");
			verify(request, times(1)).getParameter("confirmNewPass");

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
