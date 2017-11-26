package com.fdmgroup.factories;

import javax.servlet.http.HttpServletRequest;

import com.fdmgroup.entities.User;

/**
 * @author david.alejandro
 */
public class UserFactory {

	/**
	 * @param HttpServletRequest request contains the parameters needed to construct a new User 
	 * @return new User created with the request parameters
	 */
	public User createUser(HttpServletRequest request) {
		return new User(request.getParameter("regName"), request.getParameter("regUsername"),
				request.getParameter("regPassword"), request.getParameter("regEmail"), request.getParameter("regPhone"),
				request.getParameter("regAddress"), "c");
	}

}
