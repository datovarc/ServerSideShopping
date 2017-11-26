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
import com.fdmgroup.entities.Item;

public class RequestServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static RequestServlet requestServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static ItemsDAO itemDao;
	static Item item;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		requestServlet = new RequestServlet();

		itemDao = mock(ItemsDAO.class);
		item = mock(Item.class);
		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemDao);
		when(itemDao.get(1, Item.class)).thenReturn(item);
		when(item.getInventory()).thenReturn(1);
		when(item.getCost()).thenReturn(20.0);
	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("request.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("itemOrdered")).thenReturn("1");
		when(request.getParameter("itemQuantity")).thenReturn("1");
	}

	//
	// private void placeSupplierRequest(HttpServletRequest req) {
	// int iid = Integer.parseInt(req.getParameter("itemOrdered"));
	// Item item =
	// ((ItemsDAO)getServletContext().getAttribute("itemdao")).get(iid,
	// Item.class);
	// int quantity = Integer.parseInt(req.getParameter("itemQuantity")) -
	// item.getInventory();
	// double totalCost = item.getCost()*quantity;
	//
	// req.setAttribute("totalCost", totalCost);
	// req.setAttribute("item", item);
	// req.setAttribute("itemQuantity", quantity);
	// }

	@Test
	public void when_post_method_is_called_request_dispatcher_and_forward_are_called() {
		try {
			requestServlet.init(servletConfig);
			requestServlet.doPost(request, response);
			verify(request, times(1)).getRequestDispatcher("request.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
			verify(request, times(1)).getParameter("itemQuantity");
			verify(request, times(1)).getParameter("itemOrdered");
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
