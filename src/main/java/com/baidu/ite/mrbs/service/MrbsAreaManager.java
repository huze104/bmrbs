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

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.annotation.Secured;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.baidu.ite.mrbs.entity.MrbsArea;

/**
 * <p>
 * Title:MrbsArea.java
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
 * @time: 2009-02-28 22-24-51
 * 
 */

@Transactional
@Service
public class MrbsAreaManager extends AbstractManager<MrbsArea, Long> {
	final static Logger logger = LoggerFactory.getLogger(MrbsAreaManager.class);

	/**
	 * 注入需要的參數
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao = new HibernateDao<MrbsArea, Long>(sessionFactory,
				MrbsArea.class);
	}

	/**
	 * 获取所有的数据
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<MrbsArea> getAll() {
		return dao.getAll();
	}

	/**
	 * 书写一个merge方法来供flex端调用. 因为目前flex序列化过来的数据中.
	 * 存在http://www.graniteds.org/jira/browse /GDS-103错误.
	 * 即area中关联的rooms有问题，造成hibernate不会持久化数据，故而使用merge方法
	 * 
	 * @param entity
	 *            保存实体
	 */
	@Secured("AUTH_CHANGEAREANOTE")
	@Transactional
	public void merge(MrbsArea entity) {
		// 以下的用户信息获取就是为了日志之用，因为若是没有登陆，那么前边的aop权限限制就过不来
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			if (obj instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) obj;
				logger.info("修改公告 修改人：" + userDetails.getUsername() + " 内容："
						+ entity.getAreaName() + "-" + entity.getShortDescn()
						+ " --------" + entity.getDescn());
			}
		}
		dao.getSession().merge(entity);
	}
}
