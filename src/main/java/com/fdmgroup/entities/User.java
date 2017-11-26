package com.fdmgroup.entities;

import javax.persistence.*;

/**
 * 
 * @author david.alejandro
 * 
 */

@Entity
@Table(name = "rswuser")
@TableGenerator(name = "user_tab", initialValue = 1, allocationSize = 1)
public class User implements Entities {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id")
	protected int id;

	@Column
	protected String name;

	@Column
	protected String username;

	@Column
	protected String password;

	@Column
	protected String email;

	@Column
	protected String phone;

	@Column
	protected String address;

	@Column
	protected String role;

	public User() {
	}

	
	/**
	 * 
	 * @param String name - name to construct User
	 * @param String username - username to construct User
	 * @param String password - password to construct User
	 * @param String email - email to construct User
	 * @param String phone - phone to construct User
	 * @param String address - address to construct User
	 * @param String role - role to construct User
	 * 
	 */
	public User(String name, String username, String password, String email, String phone, String address,
			String role) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	/**
	 * @return int id of User
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param int id - id to set for User
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return String name of User
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param String name - name to set for User
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String username of User
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param String username - username to set for User
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return String password of User
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param String password - password to set for User
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return String email of User
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param String email - email to set for User
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return String phone of User
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * @param String phone - phone to set for User
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return String address of User
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @param String address - address to set for User
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return String role of User
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param String role - role to set for User
	 */
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", email="
				+ email + ", phone=" + phone + ", address=" + address + ", role=" + role + "]";
	}

}