package com.fdmgroup.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 
 * @author david.alejandro
 * 
 */

@Entity
@Table(name = "rswitem")
@TableGenerator(name = "item_tab", initialValue = 1, allocationSize = 1)
public class Item implements Entities {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "item_id")
	protected int id;

	@Column
	protected String name;

	@Column
	protected String description;

	@Column
	protected double price;

	@Column
	protected double cost;

	@Column
	protected int inventory;

	@ManyToOne
	protected Supplier supplier;

	public Item() {
	}

	/**
	 * 
	 * @param String name - name to construct Item
	 * @param String description - description to construct Item
	 * @param double price - price to construct Item
	 * @param double cost - cost to construct Item
	 * @param int inventory - inventory to construct Item
	 * @param Supplier supplier - supplier to construct Item
	 * 
	 */
	public Item(String name, String description, double price, double cost, int inventory, Supplier supplier) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.cost = cost;
		this.inventory = inventory;
		this.supplier = supplier;
	}

	/**
	 * @return int id of Item
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param int id - id to set for Item
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return String name of Item
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param String name - name to set for Item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String description of Item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param String description - description to set for Item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return double price for this Item
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param double price - price to set for Item
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return double cost of re-supplying this Item
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param double cost - cost to set for Item
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @return int inventory of Item
	 */
	public int getInventory() {
		return inventory;
	}

	/**
	 * @param int inventory - inventory to set for Item
	 */
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	/**
	 * @return Supplier supplier of Item
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * @param Supplier supplier - supplier to set for Item
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Item other = (Item) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", cost="
				+ cost + ", inventory=" + inventory + ", supplier=" + supplier + "]";
	}

}