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

public class LogisticServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static LogisticServlet logisticServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static ItemsDAO itemsDao;
	static Item item;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		logisticServlet = new LogisticServlet();

		requestDispatcher = mock(RequestDispatcher.class);

		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);
		itemsDao = mock(ItemsDAO.class);
		item = mock(Item.class);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemsDao);
		when(itemsDao.get(1, Item.class)).thenReturn(item);
		when(item.getInventory()).thenReturn(1);
	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("itemlogistics.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("iid")).thenReturn("1");
		when(request.getParameter("itemOrdered")).thenReturn("1");
		when(request.getParameter("itemQuantity")).thenReturn("1");

	}

	@Test
	public void when_get_method_is_called_request_dispatcher_and_forward_are_called() {
		try {
			logisticServlet.init(servletConfig);
			logisticServlet.doGet(request, response);
			verify(request, times(1)).getRequestDispatcher("itemlogistics.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void when_post_method_is_called_item_is_updated_and_request_dispatcher_and_forward_are_called() {
		try {
			logisticServlet.init(servletConfig);
			logisticServlet.doPost(request, response);
			verify(request, times(1)).getRequestDispatcher("itemlogistics.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
			verify(item, times(1)).getInventory();
			verify(itemsDao, times(1)).update(item);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
