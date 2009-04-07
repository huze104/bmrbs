/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.baidu.ite.mrbs.entity.MrbsArea;
import com.baidu.ite.mrbs.entity.MrbsRoom;
import com.baidu.ite.mrbs.entity.MrbsSchedule;
import com.baidu.ite.mrbs.entity.util.RoomSearchBean;
import com.baidu.ite.mrbs.util.DateComputorUtil;

/**
 * <p>
 * Title:MrbsRoom.java
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
 * @time: 2009-02-28 22-24-55
 * 
 */

@Transactional
@Service
public class MrbsRoomManager extends AbstractManager<MrbsRoom, Long> {
	/**
	 * 判断会议室是否已经被预定
	 */
	private static String isRoomHaveOrder = "from MrbsSchedule where ((startTime>? and startTime<?) or (endTime>? and endTime<?) or (startTime<=? and endTime>=?)) and mrbsRoom.id=?";
	/**
	 * 需要的日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 注入需要的schedulemanager来查询数据
	 */
	@Autowired
	private MrbsScheduleManager mrbsScheduleManager;

	/**
	 * 注入需要的參數
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao = new HibernateDao<MrbsRoom, Long>(sessionFactory,
				MrbsRoom.class);
	}

	/**
	 * 获取所有的数据
	 */
	public List<MrbsRoom> getAll() {
		return dao.getAll();
	}

	/**
	 * 获取某个区域内会议室信息
	 */
	public List<MrbsRoom> getAreaRooms(MrbsArea area) {
		// 对于总的办公区，将所有的会议室信息都展现出来
		if (area.getId() == 1) {
			return getAll();
		}
		return dao.findByProperty("mrbsArea.id", area.getId());
	}

	/**
	 * 查询符合条件的空余会议室<br>
	 * 
	 * @param searchBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Secured("AUTH_LOGIN")
	public List<MrbsRoom> searchIdleRooms(RoomSearchBean searchBean) {
		List<MrbsRoom> result = new ArrayList<MrbsRoom>(17);
		StringBuilder searchSql = new StringBuilder(
				"from MrbsRoom where capacity>=" + searchBean.getCapacity());
		if (searchBean.getAreaList() != null
				&& searchBean.getAreaList().size() > 0) {
			searchSql.append(" and mrbsArea.id in (");
			for (MrbsArea area : searchBean.getAreaList()) {
				searchSql.append(area.getId()).append(",");
			}
			searchSql.deleteCharAt(searchSql.length() - 1);
			searchSql.append(" )");
		}
		searchSql.append(" order by capacity");
		List<MrbsRoom> maybeRoomList = dao.find(searchSql.toString(), null);
		// 查询时间区间
		Date startDate = searchBean.getStartDate();
		Date endDate = searchBean.getEndDate();
		float tempTimeConsuming = searchBean.getTimeConsuming();
		long dayspan = DateComputorUtil.getDaysBetween(startDate, endDate);
		MrbsSchedule schedule;
		Set<MrbsSchedule> roomSchedule;
		// 如果用户搜索中限定了开始时间是必须的，那么就只查找这个时间段是否有预定
		if (searchBean.getIsMust() == 1) {
			int startHour = searchBean.getBeiginHour();
			int startMini = searchBean.getBeiginMimi();
			// 构建出来开始时间和结束时间
			startDate = DateUtils.setHours(startDate, startHour);
			startDate = DateUtils.setMinutes(startDate, startMini);
			Date endTime = DateUtils.addMinutes(startDate, new Float(
					tempTimeConsuming * 60).intValue());

			// 迭代这个会议室，确定用户需要的时间是否可用，查看会议室是否被预定的算法见 isRoomHaveOrder
			for (MrbsRoom mrbsRoom : maybeRoomList) {
				roomSchedule = new LinkedHashSet<MrbsSchedule>();
				Date tempStartTime = (Date) startDate.clone();
				Date tempEndTime = (Date) endTime.clone();
				for (int i = 0; i <= dayspan; i++) {
					// 周末就算了
					if (!DateComputorUtil.isWorkDay(tempStartTime)) {
						tempStartTime = DateUtils.addDays(tempStartTime, 1);
						tempEndTime = DateUtils.addDays(tempEndTime, 1);
						continue;
					}
					// 如果该会议室没有被预定呢
					if (!isRoomHaveOrder(mrbsRoom, tempStartTime, tempEndTime)) {
						schedule = new MrbsSchedule(mrbsRoom, tempStartTime,
								tempEndTime);
						schedule
								.setDescription("预定查询结果，本会议室可以预定，请点击进入预定界面开始预定！");
						schedule.setPreside("可供预定");
						roomSchedule.add(schedule);
					}
					tempStartTime = DateUtils.addDays(tempStartTime, 1);
					tempEndTime = DateUtils.addDays(tempEndTime, 1);
				}
				// 说明这个会议室具有可以预定
				if (roomSchedule.size() > 0) {
					mrbsRoom.setMrbsSchedules(roomSchedule);
					result.add(mrbsRoom);
				}
			}
		} else {
			// 当用户输入的开始时间只是作为参考的时候，暂时忽略掉这个选择，后续会将这个条件作为排序条件而存在
			Date tempStartTime;
			List<MrbsSchedule> schedules;
			MrbsSchedule tempsSchedule;
			MrbsSchedule tempsSchedule1;
			for (MrbsRoom mrbsRoom : maybeRoomList) {
				roomSchedule = new LinkedHashSet<MrbsSchedule>();
				tempStartTime = (Date) startDate.clone();
				tempStartTime.setHours(9);
				for (int i = 0; i <= dayspan; i++) {
					// 周末就算了
					if (!DateComputorUtil.isWorkDay(tempStartTime)) {
						tempStartTime = DateUtils.addDays(tempStartTime, 1);
						continue;
					}
					// 获取到当天该会议室的预定情况
					schedules = mrbsScheduleManager.loadTodaySchedule(mrbsRoom.getId(), tempStartTime);
					if (schedules.size() == 0) {
						// 说明会议室还没有人预定，告知用户可以预定
						schedule = new MrbsSchedule(mrbsRoom, tempStartTime,DateUtils.setHours(tempStartTime, 18));
						schedule.setDescription("预定查询结果，本会议室可以预定，请点击进入预定界面开始预定！");
						schedule.setPreside("可供预定");
						roomSchedule.add(schedule);
					} else {
						// 说明已经有预定的了，那么就判断一下哈
						for (int j = 0; j < schedules.size(); j++) {
							tempsSchedule = schedules.get(j);
							// 第一条记录需要与9点比较
							if (j == 0) {
								canSchedule(tempTimeConsuming, roomSchedule,
										tempStartTime, tempsSchedule
												.getStartTime(), mrbsRoom);
							} 
							if (j == schedules.size() - 1) {
								// 将最后一条数据与18时比对，如果能够满足预定需求，提示用户预定
								canSchedule(tempTimeConsuming, roomSchedule,
										tempsSchedule.getEndTime(), DateUtils.setHours(tempStartTime, 18),
										mrbsRoom);

							} else {
								tempsSchedule1 = schedules.get(j + 1);
								canSchedule(tempTimeConsuming, roomSchedule,
										tempsSchedule.getEndTime(),
										tempsSchedule1.getStartTime(), mrbsRoom);
							}

						}
					}
					tempStartTime = DateUtils.addDays(tempStartTime, 1);
				}
				if (roomSchedule.size() > 0) {
					mrbsRoom.setMrbsSchedules(roomSchedule);
					result.add(mrbsRoom);
				}
			}
		}
		return result;
	}

	/**
	 * @param tempTimeConsuming
	 * @param roomSchedule
	 * @param tempStartTime
	 * @param tempsSchedule
	 * @param mrbsRoom
	 */
	private void canSchedule(float tempTimeConsuming,
			Set<MrbsSchedule> roomSchedule, Date tempStartTime,
			Date tempEndTime, MrbsRoom mrbsRoom) {
		MrbsSchedule schedule;
		double hourSpan = DateComputorUtil.getHoursBetween(tempStartTime,
				tempEndTime);
		// 说明存在空余时间，可以预定
		if (hourSpan >= tempTimeConsuming) {
			schedule = new MrbsSchedule(mrbsRoom, tempStartTime, tempEndTime);
			schedule.setDescription("预定查询结果，本会议室可以预定，请点击进入预定界面开始预定！");
			schedule.setPreside("可供预定");
			roomSchedule.add(schedule);
		}
	}

	/**
	 * 判断某会议室在一定时间段内是否已经被预定
	 * 
	 * @param room
	 *            待验证的room
	 * @param tempStartTime
	 *            开始时间
	 * @param tempEndTime
	 *            结束时间
	 * @return 如果该时间段内会议室没有被预定，返回false,如果已经被预定，则返回true
	 */
	public boolean isRoomHaveOrder(MrbsRoom room, Date tempStartTime,
			Date tempEndTime) {
		return dao.find(isRoomHaveOrder, tempStartTime, tempEndTime,
				tempStartTime, tempEndTime, tempStartTime, tempEndTime,
				room.getId()).size() > 0;
	}

}
