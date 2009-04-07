/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.baidu.ite.mrbs.entity.MrbsSchedule;

/**
 * <p>
 * Title:MrbsSchedule.java
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
 * @time: 2009-02-28 22-24-56
 * 
 */

@Transactional
@Service
public class MrbsScheduleManager extends AbstractManager<MrbsSchedule, Long> {
	// 获取一段时间内的某个会议室的预定情况
	private static String TODAY_SCHEDULE = "from MrbsSchedule where startTime>? and endTime<=? and mrbsRoom.id=? order by startTime";
	private static String TODAY_AFTER_SCHEDULE = "from MrbsSchedule where startTime>? and mrbsRoom.id=? order by startTime";

	/**
	 * 注入需要的參數
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao = new HibernateDao<MrbsSchedule, Long>(sessionFactory,
				MrbsSchedule.class);
	}

	/**
	 * 获取所有的数据
	 */
	public List<MrbsSchedule> getAll() {
		return dao.getAll();
	}

	/**
	 * 获取当前时间以后，今天的某个会议室的预定情况
	 */
	@SuppressWarnings("unchecked")
	public List<MrbsSchedule> loadTodaySchedule(Long roomId,Date date) {
		if(date==null){
			date=new Date();
		}
		date=DateUtils.setHours(date, 8);
		Date today = DateUtils.setHours((Date)date.clone(), 23);
		return dao.find(TODAY_SCHEDULE, date, today, roomId);
	}

	/**
	 * 获取当前时间以后，今天及以后的某个会议室的预定情况
	 */
	@SuppressWarnings("unchecked")
	public List<MrbsSchedule> loadScheduleAfter(Long roomId) {
		Date now = new Date();
		now = DateUtils.setHours(now, 8);
		return dao.find(TODAY_AFTER_SCHEDULE, now, roomId);
	}

	public void removeById(Integer id) {
		super.remove(new Long(id));
	}
}
