package com.baidu.ite.mrbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.test.junit38.SpringTransactionalTestCase;

import com.baidu.ite.mrbs.entity.MrbsArea;
import com.baidu.ite.mrbs.entity.MrbsRoom;

/**
 * 
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
 * @time: 2009-2-21 下午01:05:24
 * 
 */
public class MrbsRoomManagerTest extends SpringTransactionalTestCase {
	@Autowired
	private MrbsRoomManager mrbsRoomManager;
	@Autowired
	private MrbsAreaManager mrbsAreaManager;

	public void testSave() {
		MrbsArea area = new MrbsArea();
		area.setAreaName("area name");
		mrbsAreaManager.save(area);

		MrbsRoom room = new MrbsRoom();
		room.setRoomName("Room name");
		room.setMrbsArea(area);
		mrbsRoomManager.save(room);
		assertTrue(room.getId() > 0);
	}

}
