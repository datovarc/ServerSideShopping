package com.fdmgroup.entities;

import javax.persistence.*;

/**
 * 
 * @author david.alejandro
 * 
 */

@Entity
@Table(name = "rswsupplier")
@TableGenerator(name = "supp_tab", initialValue = 1, allocationSize = 1)
public class Supplier implements Entities {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	protected int id;

	@Column
	protected String name;

	public Supplier() {
	}

	/**
	 * 
	 * @param String name - name to construct Supplier
	 * 
	 */
	public Supplier(String name) {
		this.name = name;
	}

	/**
	 * @return int id of Supplier
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param int id - id to set for Supplier
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return String name of Supplier
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param String name - name to set for Supplier
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Supplier other = (Supplier) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + "]";
	}

}
