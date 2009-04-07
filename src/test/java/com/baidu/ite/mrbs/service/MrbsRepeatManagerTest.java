package com.baidu.ite.mrbs.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.junit38.SpringTransactionalTestCase;

import com.baidu.ite.mrbs.entity.MrbsArea;
import com.baidu.ite.mrbs.entity.MrbsRepeat;
import com.baidu.ite.mrbs.entity.MrbsRoom;

/**
 * 
 * *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:对于MrbsRepeatManager进行测试
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
 * @time: 2009-2-21 下午01:04:15
 */
@TransactionConfiguration(defaultRollback = true)
public class MrbsRepeatManagerTest extends SpringTransactionalTestCase {
	@Autowired
	private MrbsRepeatManager mrbsRepeatManager;
	@Autowired
	private MrbsRoomManager mrbsRoomManager;
	@Autowired
	private MrbsAreaManager mrbsAreaManager;

	// 测试预定时间为当天，而且不重复
	public void testSave() {
		// 首先生成一个repeat规则，存储后，然后到数据库查看计算是否正确
		MrbsArea mrbsArea = new MrbsArea();
		mrbsArea.setLinkman("HongZhi");
		mrbsArea.setAreaName("一望秋水");
		mrbsAreaManager.save(mrbsArea);
		assertTrue(mrbsArea.getId() > 0);
		MrbsRoom room=null;
		// 首先声明一个room
		for (int i = 0; i < 10; i++) {
			room = new MrbsRoom();
			room.setCapacity(20L);
			room.setMrbsArea(mrbsArea);
			room.setDescription("7941");
			room.setRoomName("会议室" + RandomUtils.nextInt(17));
			mrbsRoomManager.save(room);
			assertTrue(room.getId() > 0);
		}
		
		// 声明一个repeat
		MrbsRepeat repeat = new MrbsRepeat();
		repeat.setStartHour("10");
		repeat.setEndHour("11");
		repeat.setStartMini("00");
		repeat.setEndMini("30");
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		repeat.setCreateBy("zhang hongzhi");
		repeat.setOrderman("周甜");
		Date startDate = new Date();
		repeat.setRepOpt(0);
		repeat.setStartDate(startDate);
		repeat.setMrbsRoom(room);
		repeat.setDescription("会议信息描述");
		mrbsRepeatManager.save(repeat);
		assertTrue(repeat.getId() > 0);
		flush();
	}

	// 测试每天都预定
	public void testSaveRepeatEveryDay() {
		// 首先生成一个repeat规则，存储后，然后到数据库查看计算是否正确
		MrbsArea mrbsArea = new MrbsArea();
		mrbsArea.setLinkman("张宏志");
		mrbsArea.setAreaName("二龙戏珠");
		mrbsAreaManager.save(mrbsArea);
		assertTrue(mrbsArea.getId() > 0);

		// 首先声明一个room
		MrbsRoom room=null;
		// 首先声明一个room
		for (int i = 0; i < 10; i++) {
			room = new MrbsRoom();
			room.setCapacity(20L);
			room.setMrbsArea(mrbsArea);
			room.setDescription("7941");
			room.setRoomName("会议室" + RandomUtils.nextInt(17));
			mrbsRoomManager.save(room);
			assertTrue(room.getId() > 0);
		}
		// 声明一个repeat
		MrbsRepeat repeat = new MrbsRepeat();
		repeat.setStartHour("10");
		repeat.setEndHour("11");
		repeat.setStartMini("00");
		repeat.setEndMini("30");
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		repeat.setCreateBy("zhang hongzhi");
		repeat.setOrderman("每天预定，10.00-11.30");
		Date startDate = new Date();
		startDate.setDate(2);
		//设置其每天都订阅
		repeat.setRepOpt(1);
		repeat.setStartDate(startDate);
		Date endDate = new Date();
		endDate.setDate(17);
		repeat.setEndDate(endDate);
		repeat.setMrbsRoom(room);
		repeat.setDescription("会议信息描述");
		mrbsRepeatManager.save(repeat);
		assertTrue(repeat.getId() > 0);
		flush();
	}

	// 测试每天都预定，並且預定全天
	public void testSaveRepeatEveryAllDay() {
		// 首先生成一个repeat规则，存储后，然后到数据库查看计算是否正确
		MrbsArea mrbsArea = new MrbsArea();
		mrbsArea.setLinkman("周甜");
		mrbsArea.setAreaName("三声炮响");
		mrbsAreaManager.save(mrbsArea);
		assertTrue(mrbsArea.getId() > 0);

		// 首先声明一个room
		MrbsRoom room=null;
		// 首先声明一个room
		for (int i = 0; i < 10; i++) {
			room = new MrbsRoom();
			room.setCapacity(20L);
			room.setMrbsArea(mrbsArea);
			room.setDescription("7941");
			room.setRoomName("会议室" + RandomUtils.nextInt(17));
			mrbsRoomManager.save(room);
			assertTrue(room.getId() > 0);
		}
		// 声明一个repeat
		MrbsRepeat repeat = new MrbsRepeat();
		repeat.setAllday(1);
		repeat.setStartHour("10");
		repeat.setEndHour("11");
		repeat.setStartMini("00");
		repeat.setEndMini("30");
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		repeat.setCreateBy("zhang hongzhi");
		repeat.setOrderman("每天、全天预定");
		Date startDate = new Date();
		startDate.setDate(17);
		//设置其每天都订阅
		repeat.setRepOpt(1);
		repeat.setStartDate(startDate);
		Date endDate = new Date();
		endDate.setDate(28);
		repeat.setEndDate(endDate);
		repeat.setMrbsRoom(room);
		repeat.setDescription("每天、全天预定");
		mrbsRepeatManager.save(repeat);
		assertTrue(repeat.getId() > 0);
		flush();
	}
	
	
	// 测试每周都预定
	public void testSaveRepeatEveryWeek() {
		// 首先生成一个repeat规则，存储后，然后到数据库查看计算是否正确
		MrbsArea mrbsArea = new MrbsArea();
		mrbsArea.setLinkman("杨志");
		mrbsArea.setAreaName("四方生平");
		mrbsAreaManager.save(mrbsArea);
		assertTrue(mrbsArea.getId() > 0);

		// 首先声明一个room
		MrbsRoom room=null;
		// 首先声明一个room
		for (int i = 0; i < 10; i++) {
			room = new MrbsRoom();
			room.setCapacity(20L);
			room.setMrbsArea(mrbsArea);
			room.setDescription("7941");
			room.setRoomName("会议室" + RandomUtils.nextInt(17));
			mrbsRoomManager.save(room);
			assertTrue(room.getId() > 0);
		}
		// 声明一个repeat
		MrbsRepeat repeat = new MrbsRepeat();
		repeat.setStartHour("10");
		repeat.setEndHour("11");
		repeat.setStartMini("00");
		repeat.setEndMini("30");
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		repeat.setCreateBy("zhang hongzhi");
		repeat.setOrderman("每周三预定，10.00-11.30");
		repeat.setRepeatWeekDay(Calendar.WEDNESDAY);
		Date startDate = new Date();
		startDate.setDate(4);
		startDate.setMonth(6);
		//设置其每周都订阅
		repeat.setRepOpt(2);
		repeat.setStartDate(startDate);
		Date endDate = new Date();
		endDate.setMonth(6);
		endDate.setDate(29);
		repeat.setEndDate(endDate);
		repeat.setMrbsRoom(room);
		repeat.setDescription("会议信息描述");
		mrbsRepeatManager.save(repeat);
		assertTrue(repeat.getId() > 0);
		flush();
	}
	
	// 测试格3周订阅周四
	public void testSaveRepeatEveryThreeWeek() {
		// 首先生成一个repeat规则，存储后，然后到数据库查看计算是否正确
		MrbsArea mrbsArea = new MrbsArea();
		mrbsArea.setLinkman("林华");
		mrbsArea.setAreaName("五福而来");
		mrbsAreaManager.save(mrbsArea);
		assertTrue(mrbsArea.getId() > 0);

		// 首先声明一个room
		MrbsRoom room=null;
		// 首先声明一个room
		for (int i = 0; i < 10; i++) {
			room = new MrbsRoom();
			room.setCapacity(20L);
			room.setMrbsArea(mrbsArea);
			room.setDescription("7986");
			room.setRoomName("会议室" + RandomUtils.nextInt(17));
			mrbsRoomManager.save(room);
			assertTrue(room.getId() > 0);
		}
		// 声明一个repeat
		MrbsRepeat repeat = new MrbsRepeat();
		repeat.setStartHour("10");
		repeat.setEndHour("11");
		repeat.setStartMini("00");
		repeat.setEndMini("30");
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		repeat.setCreateBy("zhang hongzhi");
		repeat.setOrderman("隔开3周预定周四，10.00-11.30");
		repeat.setRepeatWeekDay(Calendar.THURSDAY);
		//隔开3周
		repeat.setWeekSpan(3);
		Date startDate = new Date();
		startDate.setDate(4);
		startDate.setMonth(7);
		//设置其每周都订阅
		repeat.setRepOpt(3);
		repeat.setStartDate(startDate);
		Date endDate = new Date();
		endDate.setMonth(8);
		endDate.setDate(27);
		repeat.setEndDate(endDate);
		repeat.setMrbsRoom(room);
		repeat.setDescription("会议信息描述");
		mrbsRepeatManager.save(repeat);
		assertTrue(repeat.getId() > 0);
		flush();
	}
	
	
}
