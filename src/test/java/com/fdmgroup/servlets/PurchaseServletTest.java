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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.daos.ItemsDAO;
import com.fdmgroup.daos.UsersDAO;
import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.User;

public class PurchaseServletTest {

	static RequestDispatcher requestDispatcher;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static PurchaseServlet purchaseServlet;
	static ServletConfig servletConfig;
	static ServletContext servletContext;
	static HttpSession session;
	static ItemsDAO itemDao;
	static UsersDAO userDao;
	static ArrayList<Item> items;
	static Item item;
	static User user;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();

		purchaseServlet = new PurchaseServlet();
		items = new ArrayList<Item>();

		itemDao = mock(ItemsDAO.class);
		userDao = mock(UsersDAO.class);
		user = mock(User.class);
		item = mock(Item.class);
		requestDispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		response = mock(HttpServletResponse.class);
		servletConfig = mock(ServletConfig.class);
		servletContext = mock(ServletContext.class);

		items.add(item);

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("items")).thenReturn(items);
		when(servletContext.getAttribute("itemdao")).thenReturn(itemDao);
		when(servletContext.getAttribute("userdao")).thenReturn(userDao);
		when(userDao.get(1, User.class)).thenReturn(user);
		when(session.getAttribute("id")).thenReturn(1);

	}

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		when(request.getRequestDispatcher("purchase.jsp")).thenReturn(requestDispatcher);
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("totalPaid")).thenReturn("20");
	}

	// private void processPurchase(HttpServletRequest req) {
	//
	//
	// @SuppressWarnings("unchecked")
	// ArrayList<Item> items = (ArrayList<Item>)
	// getServletContext().getAttribute("items");
	// for(Item i : items){
	// ((ItemsDAO)getServletContext().getAttribute("itemdao")).update(i);
	// }
	// getServletContext().setAttribute("items", items);
	//
	// HttpSession session = req.getSession();
	//
	// int uid = (int)session.getAttribute("id");
	// String address =
	// ((UsersDAO)getServletContext().getAttribute("userdao")).get(uid,
	// User.class).getAddress();
	// req.setAttribute("userAddress", address);
	// }

	@Test
	public void when_get_method_is_called_request_dispatcher_and_forward_are_called() {
		try {
			purchaseServlet.init(servletConfig);
			purchaseServlet.doPost(request, response);
			verify(request, times(1)).getRequestDispatcher("purchase.jsp");
			verify(request, times(1)).getSession();
			verify(userDao, times(1)).get(1, User.class);
			verify(user, times(1)).getAddress();
			verify(requestDispatcher, times(1)).forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
