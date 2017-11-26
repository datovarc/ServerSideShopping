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

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.entities.Item;

public class ItemDetailsServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static ItemDetailsServlet itemDetailsServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static ItemsDAO itemsDao;
	static Item item;
	
	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		
		itemDetailsServlet = new ItemDetailsServlet();
		
		requestDispatcher = mock(RequestDispatcher.class);
		
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);
		itemsDao = mock(ItemsDAO.class);
		item = mock(Item.class);
		
		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemsDao);
		when(itemsDao.get(1,Item.class)).thenReturn(item);
	}
	
	@Before
	public void setup(){
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("itemdetails.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("iid")).thenReturn("1");

	}
	
	@Test
	public void when_get_method_is_called_requestdispatcher_and_forward_are_called() {
		try {
			itemDetailsServlet.init(servletConfig);
			itemDetailsServlet.doGet(request, response);
			verify(request,times(1)).getRequestDispatcher("itemdetails.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
