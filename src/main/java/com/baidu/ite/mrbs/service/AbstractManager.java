package com.baidu.ite.mrbs.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:提供基本的保存，删除，查询功能，相当于dao的一个代理类<br>
 * 事务控制在service层控制，子类在构造函数中提供ado的实例化即可完成基本功能的必要条件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:baidu
 * </p>
 * 
 * @author: zhanghongzhi lanfanss@126.com,zhanghongzhi@baidu.com
 * @version: 0.1
 * @time: 2008-10-15 上午08:45:32
 * 
 * 
 */
@Transactional
public abstract class AbstractManager<T, PK extends Serializable> {
	/**
	 * dao对象，用于单一实体类的增删改查等基本操作
	 */
	protected HibernateDao<T, PK> dao;

	/**
	 * 更新或者新增实体
	 * 
	 * @param entity
	 *            需要更新或者新增的对象实体
	 */
	public void save(T entity) {
		dao.save(entity);
	}

	/**
	 * 删除实体
	 * @param entity
	 */
	public void remove(T entity) {
		dao.delete(entity);
	}

	public void remove(PK id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return dao.findByCriteria();
	}

	@Transactional(readOnly = true)
	public Page<T> findAll(Page<T> page) {
		return dao.findByCriteria(page);
	}

	/**
	 * 按id获取对象.
	 */
	@Transactional(readOnly = true)
	public T load(final PK id) {
		return (T) dao.get(id);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 */

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List find(String hql, Object... values) {
		return dao.find(hql, values);
	}

	/**
	 * 按HQL分页查询. 暂不支持自动获取总结果数,需用户另行执行查询.
	 * 
	 * @param page
	 *            分页参数.包括pageSize 和firstResult.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的参数.
	 * 
	 * @return 分页查询结果,附带结果列表及所有查询时的参数.
	 */
	@Transactional(readOnly = true)
	public Page<T> find(Page<T> page, String hql, Object... values) {
		return dao.find(page, hql, values);
	}

}
