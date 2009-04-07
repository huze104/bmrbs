package com.baidu.ite.mrbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.junit38.SpringTransactionalTestCase;

import com.baidu.ite.mrbs.entity.MrbsArea;

/**
 * 
 * *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:对area数据库表进行测试
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
 * @time: 2009-2-21 下午12:07:39
 * 
 */
@org.springframework.test.context.ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class MrbsAreaManagerTest extends SpringTransactionalTestCase {
	@Autowired
	private MrbsAreaManager mrbsAreaManager;

	/**
	 * 新增数据到数据库中，判断id>0作为通过标准
	 */
	public void testSave() {
		MrbsArea area = new MrbsArea();
		area.setAreaName("测试Area");
		area.setLinkman("some thing like ");
		mrbsAreaManager.save(area);
		assertTrue(area.getId() > 0);
	}

	/**
	 * 首先新增数据到数据,将其flush（此时认为数据）
	 */
	public void testUpdate() {
		MrbsArea area = new MrbsArea();
		area.setAreaName("测试Area");
		area.setLinkman("some thing like ");
		mrbsAreaManager.save(area);
		assertTrue(area.getId() > 0);
		area.setDescn("changeArea");
		mrbsAreaManager.save(area);
		assertTrue(true);
	}

}
