package com.fdmgroup.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Entities;

/**
 * @author david.alejandro
 * Generic used where T can be a Entities or subclass of it
 */
public abstract class GenericDAO<T extends Entities> {

	protected EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction et;

	public GenericDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * @return EntityManager created from EntityManagerFacotry emf
	 */
	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * @param T entity - entity to be persisted
	 */
	public void add(T entity) {
		begin();
		em.persist(entity);
		clearUp();
	}

	/**
	 * @param int id of Entities to be obtained
	 * @param Class<T> entityClass is the class of the Entities subclass to be obtained 
	 * @return T entity
	 */
	public T get(int id, Class<T> entityClass) {

		begin();
		T entity = em.find(entityClass, id);
		clearUp();
		return entity;

	}

	/**
	 * @param int id of Entities to be removed
	 * @param Class<T> entityClass is the class of the Entities subclass to be removed
	 */
	public void remove(int id, Class<T> entityClass) {

		begin();
		T entity = em.find(entityClass, id);
		if (entity != null) {
			em.remove(entity);
		}
		clearUp();
	}

	/**
	 * @param T entity - entity to be updated
	 */
	public void update(T entity) {
		begin();

		em.merge(entity);

		clearUp();
	}

	/**
	 * @param Class<T> entityClass is the class of the Entities subclass to be listed
	 * @return List<T> of Entities resulting from query
	 */
	public List<T> list(Class<T> entityClass) {
		String query = "SELECT e from " + entityClass.getSimpleName() + " e";
		TypedQuery<T> results = getEntityManager().createQuery(query, entityClass);

		return results.getResultList();
	}

	/**
	 * Obtains the EntityManager em
	 * Obtains the EntityTransaction et
	 * Initiates the EntityTranstaction et
	 */
	protected void begin() {
		em = getEntityManager();
		et = em.getTransaction();
		et.begin();
	}

	/**
	 * Commits changes made through the EntityTransaction et
	 * Closes the EntityManager em
	 */
	protected void clearUp() {
		et.commit();
		em.close();
	}

}
