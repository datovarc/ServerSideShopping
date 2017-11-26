package com.fdmgroup.entities;

import java.util.Date;
import javax.persistence.*;

/**
 * 
 * @author david.alejandro
 * 
 */

@Entity
@Table(name = "rsworder")
@TableGenerator(name = "order_tab", initialValue = 1, allocationSize = 1)
public class Order implements Entities {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "order_id")
	protected int id;

	@Column(insertable = false, updatable = false, name = "user_id")
	protected int userId;

	@Column(name = "item_id")
	protected int itemId;

	@Column
	protected int quantity;

	@Column
	protected double price;

	@Column(name = "placement_date")
	protected Date placementDate;

	@Column(name = "delivery_date")
	protected Date deliveryDate;

	@Column
	protected String address;

	@Column
	protected String status;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	protected User user;

	public Order() {
	}

	/**
	 * 
	 * @param int userId - userId to construct Order
	 * @param int itemId - itemId to construct Order
	 * @param int quantity - quantity to construct Order
	 * @param double price - price to construct Order
	 * @param Date placementDate - placementDate to construct Order
	 * @param Date deliveryDate - deliveryDate to construct Order
	 * @param String address - address to construct Order
	 * @param String status - status to construct Order
	 * @param User user - user to construct Order
	 * 
	 */
	public Order(int userId, int itemId, int quantity, double price, Date placementDate, Date deliveryDate,
			String address, String status, User user) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
		this.placementDate = placementDate;
		this.deliveryDate = deliveryDate;
		this.address = address;
		this.status = status;
		this.user = user;
	}

	/**
	 * @return int id of Order
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param int id - id to set for Order
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return int userId of Order
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param int userId - userId to set for Order
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return int itemId of Order
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param int itemId - itemId to set for Order
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return int quantity of Order
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param int quantity - quantity to set for Order
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return double price of Order
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param double price - price to set for Order
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return date placementDate of Order
	 */
	public Date getPlacementDate() {
		return placementDate;
	}

	/**
	 * @param Date placementDate - placementDate to set for Order
	 */
	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}

	/**
	 * @return date deliveryDate of Order
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param Date deliveryDate - deliveryDate to set for Order
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return String status of Order
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param String status - status to set for Order
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return String address of Order
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param String address - address to set for Order
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return User user of Order
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param User user - user to set for Order
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		result = prime * result + id;
		result = prime * result + itemId;
		result = prime * result + ((placementDate == null) ? 0 : placementDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + userId;
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
		Order other = (Order) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
		if (id != other.id)
			return false;
		if (itemId != other.itemId)
			return false;
		if (placementDate == null) {
			if (other.placementDate != null)
				return false;
		} else if (!placementDate.equals(other.placementDate))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user_id=" + userId + ", item_id=" + itemId + ", quantity=" + quantity + ", price="
				+ price + ", placementDate=" + placementDate + ", deliveryDate=" + deliveryDate + ", address=" + address
				+ ", status=" + status + ", user=" + user + "]";
	}

}
