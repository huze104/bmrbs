/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.baidu.ite.mrbs.entity.MrbsRepeat;
import com.baidu.ite.mrbs.entity.MrbsSchedule;
import com.baidu.ite.mrbs.util.DateComputorUtil;

/**
 * *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:baidu
 * </p>
 * 
 * @author: 张宏志(zhanghongzhi@baidu.com)
 * @version: 0.1
 * @time: 2009-3-3 上午10:23:04
 * 
 */

@Transactional
@Service
public class MrbsRepeatManager extends AbstractManager<MrbsRepeat, Long> {
	final static Logger logger = LoggerFactory.getLogger(MrbsRepeatManager.class);

	/**
	 * 注入需要的參數
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao = new HibernateDao<MrbsRepeat, Long>(sessionFactory,
				MrbsRepeat.class);
	}

	@Autowired
	private MrbsRoomManager mrbsRoomManager;

	/**
	 * 获取所有的数据
	 */
	public List<MrbsRepeat> getAll() {
		return dao.getAll();
	}

	/**
	 * 重写保存和更新方法，加入根据这个规则计算出会议室预定情况
	 */
	@Secured("AUTH_LOGIN")
	public boolean saveRepeat(MrbsRepeat repeat) {
		logger.info("准备预定会议室，规则如下："
				+ ToStringBuilder.reflectionToString(repeat));
		// 判断是否能够预定
		boolean canGenerator = generatorScheduleByRule(repeat);
		if (!canGenerator) {
			logger.info("预定失败");
			return false;
		}
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		super.save(repeat);
		logger
				.info("预定成功 产生结果如下："
						+ ToStringBuilder.reflectionToString(repeat));
		logger.info("预定完成");
		return true;
	}

	/**
	 * 判断该规则下能否产生数据<br>
	 * 根据规则计算出来<br>
	 *规则1： 如果是选择全天的，则开始时间为9点，结束时间为18点<br>
	 *规则2：如果不重复，则说明只是定当天的，开始时间和结束时间都是当天<br>
	 *当中判断的时候，还需要考虑到是否为改变规则<br>
	 *判断逻辑：<br>
	 *每次计算出来一个schedule的时候，就到数据库中判断一下是否有这个会议室被定下了，如果已经订阅了，那么返回false就行了<br>
	 * 
	 * 
	 * @param entity
	 * @return
	 */
	private boolean generatorScheduleByRule(MrbsRepeat repeat) {
		Set<MrbsSchedule> schedules = new HashSet<MrbsSchedule>(17);
		MrbsSchedule schedule;
		// 当预定了一整天的时候,将开始时间和结束时间都重设
		if (repeat.getAllday() == 1) {
			repeat.setStartHour("9");
			repeat.setStartMini("00");
			repeat.setEndHour("18");
			repeat.setEndMini("00");
		}
		// 如果是不重复情况，则将结束日期也设置成为当天，并且生成schedule,返回
		Date startTime = repeat.getStartDate();
		Date endTime = repeat.getEndDate();
		// 如果没有技术日期，就以当天为算
		if (endTime == null) {
			endTime = startTime;
		}
		// 获取到开始日期
		startTime = DateUtils.setHours(startTime, Integer.valueOf(repeat
				.getStartHour()));
		startTime = DateUtils.setMinutes(startTime, Integer.valueOf(repeat
				.getStartMini()));
		endTime = DateUtils.setHours(endTime, Integer.valueOf(repeat
				.getEndHour()));
		endTime = DateUtils.setMinutes(endTime, Integer.valueOf(repeat
				.getEndMini()));
		repeat.setStartDate(startTime);
		repeat.setEndDate(endTime);
		// 建立两个临时变量供schedule用
		Date tempStartTime = (Date) startTime.clone();
		Date tempEndTime = DateUtils.setHours(tempStartTime, Integer
				.valueOf(repeat.getEndHour()));
		tempEndTime = DateUtils.setMinutes(tempEndTime, Integer.valueOf(repeat
				.getEndMini()));
		if (repeat.getRepOpt() == 0) {
			schedule = new MrbsSchedule(repeat, tempStartTime, tempEndTime);
			if (mrbsRoomManager.isRoomHaveOrder(repeat.getMrbsRoom(),
					tempStartTime, tempEndTime)) {
				logger.warn("预定失败：" + repeat.getMrbsRoom().getRoomName() + "使用"
						+ tempStartTime + "-" + tempEndTime);
				return false;
			}
			// 经计算结果放置到repeat中
			schedules.add(schedule);
		} else if (repeat.getRepOpt() == 1) {
			// 说明每天都重复
			// 第一步计算出来隔了多少天(只算工作日)，然后每天都生成一个schedule
			// FIXME(我现在觉得自己这么设计似乎不好，味道不好！)
			long daySpan = DateComputorUtil.getDaysBetween(startTime, endTime);
			// 循环每一天
			for (int i = 0; i <= daySpan; i++) {
				if (DateComputorUtil.isWorkDay(tempStartTime)) {
					schedule = new MrbsSchedule(repeat, tempStartTime,
							tempEndTime);
					if (mrbsRoomManager.isRoomHaveOrder(repeat.getMrbsRoom(),
							tempStartTime, tempEndTime)) {
						logger.warn("预定失败：" + repeat.getMrbsRoom().getRoomName() + "使用"
								+ tempStartTime + "-" + tempEndTime);
						return false;
					}
					// 经计算结果放置到repeat中
					schedules.add(schedule);
				}
				tempStartTime = DateUtils.addDays(tempStartTime, 1);
				tempEndTime = DateUtils.addDays(tempEndTime, 1);
			}
		} else if (repeat.getRepOpt() == 2 || repeat.getRepOpt() == 3) {
			int weeks = 1;
			if (repeat.getRepOpt() == 3) {
				weeks = repeat.getWeekSpan();
			}
			// 每周都重复或者是隔开n周
			long weekSpan = DateComputorUtil
					.getWeeksBetween(startTime, endTime)
					/ weeks;
			tempStartTime = DateComputorUtil.getNextWeekDay(tempStartTime,
					repeat.getRepeatWeekDay());
			tempEndTime = DateComputorUtil.getNextWeekDay(tempEndTime, repeat
					.getRepeatWeekDay());
			for (int i = 0; i <= weekSpan; i++) {
				if (tempEndTime.after(endTime)) {
					break;
				}
				schedule = new MrbsSchedule(repeat, tempStartTime, tempEndTime);
				if (mrbsRoomManager.isRoomHaveOrder(repeat.getMrbsRoom(),
						tempStartTime, tempEndTime)) {
					logger.warn("预定失败：" + repeat.getMrbsRoom().getRoomName() + "使用"
							+ tempStartTime + "-" + tempEndTime);
					return false;
				}
				// 经计算结果放置到repeat中
				schedules.add(schedule);
				tempStartTime = DateUtils.addDays(tempStartTime, 7 * weeks);
				tempEndTime = DateUtils.addDays(tempEndTime, 7 * weeks);
			}
		}
		repeat.setMrbsSchedules(schedules);
		return true;
	}

}
