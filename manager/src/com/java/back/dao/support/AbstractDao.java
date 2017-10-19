package com.java.back.dao.support;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	public abstract Class<T> getEntityClass();

	public String getHql = "from " + getEntityClass().getName() + " where 1=1 ";

	// 采用getCurrentSession()创建的session会绑定到当前线程中，而采用openSession()
	// 创建的session则不会
	public Session findSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return findSession().createCriteria(getEntityClass()).list();
	}

	/**
	 * 根据参数查询对象
	 * 
	 * @param pro
	 *            参数名
	 * @param val
	 *            参数值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(String pro, Object val) {
		return (T) findSession().createCriteria(getEntityClass())
				.add(Restrictions.eq(pro, val)).uniqueResult();
	}

	/**
	 * 根据一个参数查询列表
	 * 
	 * @param pro
	 *            参数名
	 * @param val
	 *            参数值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String pro, Object val) {
		return findSession().createCriteria(getEntityClass())
				.add(Restrictions.eq(pro, val)).list();
	}

	/**
	 * 查询所有数据条数
	 * 
	 * @return
	 */
	public int findCounnt() {
		String query = "select count(*) from " + getEntityClass().getName();
		return Integer.parseInt(findSession().createQuery(query).uniqueResult()
				.toString());
	}

	public int findCountLike(String pro, String val) {
		String query = "select count(*) from " + getEntityClass().getName()
				+ " where " + pro + " like '%" + val + "%'";
		return Integer.parseInt(findSession().createQuery(query).uniqueResult()
				.toString());
	}

	/**
	 * 根据一个参数查询条数
	 * 
	 * @param pro
	 *            参数名
	 * @param searchValue
	 *            参数值
	 * @return
	 */
	public int findCountByProperty(String pro, Object searchValue) {
		String query = "select count(*) from " + getEntityClass().getName()
				+ " where " + pro + " = '" + searchValue + "'";
		return Integer.parseInt(findSession().createQuery(query).uniqueResult()
				.toString());
	}

	/**
	 * 根据in参数删除
	 * 
	 * @param pro
	 * @param value
	 */
	public int deleteByPropertyIn(String pro, String value) {
		String query = "delete from " + getEntityClass().getName() + " where "
				+ pro + " in " + value;
		return findSession().createQuery(query).executeUpdate();
	}

	/**
	 * 根据类型为long ,int形式的参数删除
	 * 
	 * @param pro
	 * @param val
	 */
	public int deleteByPropertyString(String pro, Object val) {
		String query = "delete from " + getEntityClass().getName() + " where "
				+ pro + "=" + val+ "";
		return findSession().createQuery(query).executeUpdate();
	}
	/**
	 * 删除数据
	 * @param pro
	 * @param val
	 * @return
	 */
	public int deleteByProperty(String pro, Object val) {
		String query = "delete from " + getEntityClass().getName() + " where "
				+ pro + "='" + val.toString() + "'";
		return findSession().createQuery(query).executeUpdate();
	}
	
	/**
	 * 根据id查询单个对象
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(long id) {
		return (T) findSession().get(getEntityClass().getName(), id);
	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 */
	public boolean save(Object obj) {
		try {
			findSession().save(obj);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 */
	public boolean delete(Object obj) {
		try {
			findSession().delete(obj);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param obj
	 */
	public boolean update(Object obj) {
		try {
			findSession().update(obj);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据sql语句查询条数
	 * 
	 * @param sql
	 * @return
	 */
	public int countAll(String sql) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createSQLQuery(sql);
		int parseInt = Integer.parseInt(query.uniqueResult().toString());
		return parseInt;
	}

	/**
	 * 根据hql查询条数
	 * 
	 * @param hql
	 * @return
	 */
	public int countHqlAll(String hql) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createQuery(hql);
		int parseInt = Integer.parseInt(query.uniqueResult().toString());
		return parseInt;
	}

	/**
	 * sql语句分页查询
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	public List<Object[]> querySqlForList(String sql, int page) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createSQLQuery(sql);
		query.setMaxResults(page);
		List<Object[]> list = query.list();
		return list;
	}

	/**
	 * hql语句分页查询
	 * 
	 * @param hql
	 * @param params
	 * @param pagesize
	 * @return
	 */
	public List<T> queryHqlForList(String hql, Object[] params, int pagesize) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createQuery(hql);
		query = setParamForquery(query, params);
		query.setMaxResults(pagesize);
		return query.list();
	}

	/**
	 * 给query设置参数
	 * 
	 * @param query
	 * @param params
	 * @return
	 */
	private Query setParamForquery(Query query, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof String) {
					query.setString(i, params[i].toString());
				} else if (params[i] instanceof Integer) {
					query.setInteger(i, Integer.parseInt(params[i].toString()));
				} else if (params[i] instanceof java.sql.Date) {
					query.setDate(i, new Date(params[i].toString()));
				} else if (params[i] instanceof Long) {
					query.setLong(i, Long.parseLong(params[i].toString()));
				}
			}
		}
		return query;
	}

	/**
	 * 根据sql获取列表
	 * 
	 * @param sql
	 * @param params
	 *            参数
	 * @return
	 */
	public List<Object[]> getListBySql(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createSQLQuery(sql);
		query = setParamForquery(query, params);
		return query.list();
	}

	/**
	 * hql语句条件查询
	 * 
	 * @param hql
	 * @param params
	 *            参数
	 * @return
	 */
	public List<T> queryHqlForList(String hql, Object[] params) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createQuery(hql);
		query = setParamForquery(query, params);
		return query.list();
	}

	/**
	 * 执行带有参数的sql语句
	 * 
	 * @param sql
	 * @param params
	 *            (传null 为不带参数)
	 * @return
	 */
	public int executeBySql(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Query query = this.findSession().createSQLQuery(sql);
		query = setParamForquery(query, params);
		return query.executeUpdate();
	}

	/**
	 * 根据主键获取单个对象
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return (T) findSession().get(getEntityClass(), id);
	}

	/**
	 * sql语句查询结果映射为map形式
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryResultToMap(String sql) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = findSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	/**
	 * 保存或修改对象
	 * 
	 * @param object
	 * @return
	 */
	public boolean saveOrUpdate(Object object) {
		// TODO Auto-generated method stub
		try {
			findSession().merge(object);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
