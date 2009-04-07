/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.service;

import java.util.List;

import org.granite.messaging.service.ServiceException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.baidu.ite.mrbs.entity.Authority;
import com.baidu.ite.mrbs.entity.MrbsUser;
import com.baidu.ite.mrbs.entity.Role;

/**
 * <p>
 * Title:MrbsUser.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:baidu
 * </p>
 * 
 * @author: zhanghongzhi@baidu.com
 * @version: 0.1
 * @time: 2009-02-28 22-24-59
 * 
 */

@Transactional
@Service
public class MrbsUserManager {

	private static String AUTH_HQL = "select count(u) from User u where u.loginName=? and u.password=?";

	private HibernateDao<MrbsUser, Long> userDao;

	private HibernateDao<Role, Long> roleDao;

	private HibernateDao<Authority, Long> authDao;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userDao = new HibernateDao<MrbsUser, Long>(sessionFactory,
				MrbsUser.class);
		roleDao = new HibernateDao<Role, Long>(sessionFactory,
				Role.class);
		authDao = new HibernateDao<Authority, Long>(sessionFactory,
				Authority.class);
	}

	@Transactional(readOnly = true)
	public MrbsUser getUser(Long id) {
		return userDao.get(id);
	}

	@Transactional(readOnly = true)
	public Page<MrbsUser> getAllUsers(Page<MrbsUser> page) {
		return userDao.getAll(page);
	}

	@Transactional(readOnly = true)
	public MrbsUser getUserByLoginName(String loginName) {
		return userDao.findUniqueByProperty("loginName", loginName);
	}

	/**
	 * 持久化对象，并且将该对象返回<br>
	 * 如此，flex客户端就可以拿到一个拥有id的对象
	 * 
	 * @param user
	 * @return
	 */
	public MrbsUser saveUser(MrbsUser user) {
		userDao.save(user);
		return user;
	}

	public void deleteUser(Long id) {
		if (id == 1)
			throw new ServiceException("不能删除超级用户");

		MrbsUser user = userDao.get(id);
		userDao.delete(user);
	}

	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String orgLoginName) {
		return userDao.isPropertyUnique("loginName", loginName, orgLoginName);
	}

	/**
	 * 验证用户名密码. 因为使用acegi做安全管理,此函数已作废,仅用作demo.
	 * 
	 * @return 验证通过时返回true.用户名或密码错误返回false.
	 */
	@Transactional(readOnly = true)
	public boolean auth(String loginName, String password) {
		if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password))
			return false;

		return (userDao.findLong(AUTH_HQL, loginName, password) == 1);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRoles() {
		return roleDao.getAll();
	}

	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	public void saveRole(Role role) {
		roleDao.save(role);
	}

	public void deleteRole(Long id) {
		if (id == 1)
			throw new ServiceException("不能删除超级管理员角色");
		Role role = roleDao.get(id);
		roleDao.delete(role);
	}

	@Transactional(readOnly = true)
	public List<Authority> getAllAuths() {
		return authDao.getAll();
	}

	@Transactional(readOnly = true)
	public Authority getAuthority(Long id) {
		return authDao.get(id);
	}
}
