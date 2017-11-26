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

public class LogoutServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static LogoutServlet logoutServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		logoutServlet = new LogoutServlet();

		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void when_get_method_is_called_request_dispatcher_and_forward_are_called() {
		try {
			logoutServlet.init(servletConfig);
			logoutServlet.doGet(request, response);
			verify(request, times(1)).getRequestDispatcher("index.jsp");
			verify(request, times(1)).getSession();
			verify(session, times(1)).invalidate();
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
