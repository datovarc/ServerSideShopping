package com.fdmgroup.factories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.BasicConfigurator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.entities.User;

public class UserFactoryTest {

	static UserFactory userFactory;
	static HttpServletRequest request;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
		userFactory = new UserFactory();
		request = mock(HttpServletRequest.class);

		when(request.getParameter("regName")).thenReturn("test");
		when(request.getParameter("regUsername")).thenReturn("test");
		when(request.getParameter("regPassword")).thenReturn("test");
		when(request.getParameter("regEmail")).thenReturn("test");
		when(request.getParameter("regPhone")).thenReturn("test");
		when(request.getParameter("regAddress")).thenReturn("test");
	}

	@Test
	public void test_when_creating_a_user_return_user_with_mock_request_attributes() {
		User user = userFactory.createUser(request);
		assertEquals("test", user.getName());
	}

}
