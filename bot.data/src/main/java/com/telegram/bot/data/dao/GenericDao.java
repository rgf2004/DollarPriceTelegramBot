package com.telegram.bot.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

public interface GenericDao<T> extends Serializable
{
   public Query createQuery(String queryStr);
   
   public T createEntity(T t);
   
   public void deleteEntity(Object id);

   public T findEntity(Object id);

   public Object getSingleResultObject(Query query);
   
   public Object getSingleResultObjectByNamedQuery(String queryName, Map<String, Object> queryParams);

   public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

   public void deleteEntity(Long id);

   public T findEntity(Long id);

   public List<T> findAll();

   public Long count();

   public void flush();

   public void clear();

   public int deleteByNamedQuery(String queryName, Map<String, Object> queryParams);  
}
