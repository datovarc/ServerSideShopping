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

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.daos.OrdersDAO;
import com.fdmgroup.entities.Order;

public class DeliveryServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static DeliveryServlet deliveryServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static OrdersDAO ordersDao;
	static ArrayList<Order> orders;
	static Order order;
	
	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		
		deliveryServlet = new DeliveryServlet();
		orders = new ArrayList<Order>();
		
		requestDispatcher = mock(RequestDispatcher.class);
		
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);
		ordersDao = mock(OrdersDAO.class);
		order = mock(Order.class);
		
		orders.add(order);
		
		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("orderdao")).thenReturn(ordersDao);
		when(ordersDao.list(Order.class)).thenReturn(orders);
		when(ordersDao.get(1,Order.class)).thenReturn(order);
	}
	
	@Before
	public void setup(){
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("deliverycontrol.jsp")).thenReturn(requestDispatcher);
		when(request.getParameter("orderStatus")).thenReturn("Delivered");
		when(request.getParameter("orderEdited")).thenReturn("1");
	}
	
	@Test
	public void when_get_method_is_called_requestdispatcher_and_forward_are_called() {
		try {
			deliveryServlet.init(servletConfig);
			deliveryServlet.doGet(request, response);
			verify(request,times(1)).getRequestDispatcher("deliverycontrol.jsp");
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void when_post_method_is_called_requestdispatcher_and_forward_are_called() {
		try {
			deliveryServlet.init(servletConfig);
			deliveryServlet.doPost(request, response);
			
			verify(request,times(1)).getRequestDispatcher("deliverycontrol.jsp");
			verify(order, times(1)).setStatus("Delivered");
			verify(ordersDao, times(1)).update(order);
			verify(requestDispatcher, times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
