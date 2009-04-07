package com.baidu.ite.mrbs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.junit38.SpringTransactionalTestCase;

import com.baidu.ite.mrbs.entity.MrbsArea;
import com.baidu.ite.mrbs.entity.MrbsRepeat;
import com.baidu.ite.mrbs.entity.MrbsRoom;
import com.baidu.ite.mrbs.entity.MrbsSchedule;

@TransactionConfiguration(defaultRollback = true)
public class MrbsScheduleManagerTest extends SpringTransactionalTestCase {
	@Autowired
	private MrbsScheduleManager mrbsScheduleManager;
	@Autowired
	private MrbsRepeatManager mrbsRepeatManager;
	@Autowired
	private MrbsRoomManager mrbsRoomManager;
	@Autowired
	private MrbsAreaManager mrbsAreaManager;

	/**
	 * 对于系统中的schedule提供save测试
	 */
	public void testSave() {
		MrbsArea mrbsArea = new MrbsArea();
		mrbsArea.setLinkman("some thing");
		mrbsArea.setAreaName("here");
		mrbsAreaManager.save(mrbsArea);
		assertTrue(mrbsArea.getId() > 0);

		// 首先声明一个room
		MrbsRoom room = new MrbsRoom();
		room.setCapacity(20L);
		room.setMrbsArea(mrbsArea);
		room.setDescription(RandomStringUtils.random(23, new char[] { '需', '要',
				'刷', '新', '本', '页', '面', '即', '可', '不', '需', '要', '重', '新',
				'认', '证' }));
		room.setRoomName(RandomStringUtils.random(10, new char[] { '需', '要',
				'刷', '新', '本', '页', '面', '即', '可', '不', '需', '要', '重', '新',
				'认', '证' }));
		mrbsRoomManager.save(room);
		assertTrue(room.getId() > 0);
		flush();
		// 首先声明一个repeat
		MrbsRepeat repeat = new MrbsRepeat();
		int startHour = RandomUtils.nextInt(17);
		int endHour = startHour + RandomUtils.nextInt(6);
		repeat.setStartHour(startHour + "");
		repeat.setEndHour(endHour + "");
		repeat.setEndMini("30");
		repeat.setStartMini("00");
		repeat.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		repeat.setCreateBy(RandomStringUtils.random(10, new char[] { '需', '要',
				'刷', '新', '本', '页', '面', '即', '可', '不', '需', '要', '重', '新',
				'认', '证' }));
		repeat.setOrderman(RandomStringUtils.random(16, new char[] { '需', '要',
				'刷', '新', '本', '页', '面', '即', '可', '不', '需', '要', '重', '新',
				'认', '证' }));
		Date startDate = new Date();
		repeat.setRepOpt(0);
		// 设置好开始时间和结束时间
		startDate = DateUtils.addDays(startDate, RandomUtils.nextInt(10));
		repeat.setStartDate(startDate);
		repeat
				.setEndDate(DateUtils.addDays(startDate, RandomUtils
						.nextInt(10)));
		repeat.setMrbsRoom(room);
		repeat.setDescription("some");
		mrbsRepeatManager.save(repeat);
		assertTrue(repeat.getId() > 0);
		// 加入schedule测试
		for (int i = 0; i < 100; i++) {
			MrbsSchedule mrbsSchedule = new MrbsSchedule();
			mrbsSchedule.setCreateBy("hongzhi");
			mrbsSchedule.setMrbsRepeat(repeat);
			mrbsSchedule.setStartTime(startDate);
			mrbsSchedule.setEndTime(DateUtils.addDays(startDate, RandomUtils
					.nextInt(10)));
			mrbsSchedule.setMrbsRoom(room);
			mrbsSchedule.setPreside(RandomStringUtils.random(16, new char[] {
					'需', '要', '刷', '新', '本', '页', '面', '即', '可', '不', '需', '要',
					'重', '新', '认', '证' }));
			mrbsSchedule.setDescription(RandomStringUtils.random(16,
					new char[] { '需', '要', '刷', '新', '本', '页', '面', '即', '可',
							'不', '需', '要', '重', '新', '认', '证' }));
			mrbsSchedule.setNum(5);
			mrbsScheduleManager.save(mrbsSchedule);
			assertTrue(mrbsSchedule.getId() > 0);
		}
		flush();
	}

	public void testRemove() {
		List<MrbsSchedule> list = mrbsScheduleManager.findAll();
		if (list.size() > 0) {
			System.out.println(list.get(0).getId());
			mrbsScheduleManager.remove(list.get(0));
			flush();
		}

	}

}
