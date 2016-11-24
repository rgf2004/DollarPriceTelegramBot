package com.telegram.bot.data.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.util.CollectionUtils;

import com.telegram.bot.data.dao.GenericDao;

public class JPADao<T> implements GenericDao<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1624064411648787311L;

	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	public JPADao() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	public void flush() {
		this.em.flush();
	}

	public void clear() {
		this.em.clear();
	}

	/**
	 * @param t
	 * @return t
	 */
	public T createEntity(final T t) {
		this.em.persist(t);
		return t;
	}

	public void deleteEntity(final Object id) {
		this.em.remove(this.em.getReference(type, id));
	}

	public void deleteEntity(final Long id) {
		this.em.remove(this.em.getReference(type, id));
	}

	public T findEntity(final Object id) {
		return (T) this.em.find(type, id);
	}

	public T findEntity(final Long id) {
		return (T) this.em.find(type, id);
	}

	public T updateEntity(final T t) {
		return this.em.merge(t);
	}

	@SuppressWarnings("rawtypes")
	public Object getSingleResultObject(Query query) {
		List list = query.getResultList();
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public Object getSingleResultObjectByNamedQuery(String queryName, Map<String, Object> queryParams) {
		Query namedQuery = em.createNamedQuery(queryName);

		if (queryParams != null) {
			for (String s : queryParams.keySet()) {
				namedQuery.setParameter(s, queryParams.get(s));
			}
		}

		List list = namedQuery.getResultList();

		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public Query createQuery(String queryStr) {
		return em.createQuery(queryStr, type);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
		Query namedQuery = em.createNamedQuery(queryName);

		if (queryParams != null) {
			for (String s : queryParams.keySet()) {
				namedQuery.setParameter(s, queryParams.get(s));
			}
		}

		return namedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public int deleteByNamedQuery(String queryName, Map<String, Object> queryParams) {
		Query namedQuery = em.createNamedQuery(queryName);

		if (queryParams != null) {
			for (String s : queryParams.keySet()) {
				namedQuery.setParameter(s, queryParams.get(s));
			}
		}

		return namedQuery.executeUpdate();
	}

	public List<T> findAll() {
		Query query = em.createQuery("from " + type.getSimpleName());
		return query.getResultList();
	}

	public Long count() {
		Query query = em.createQuery("select count(o) from " + type.getSimpleName() + " o ");
		Long count = (Long) query.getSingleResult();

		return count;
	}

}
