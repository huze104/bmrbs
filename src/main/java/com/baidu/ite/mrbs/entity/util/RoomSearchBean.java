package com.baidu.ite.mrbs.entity.util;

import java.util.Date;
import java.util.List;

import com.baidu.ite.mrbs.entity.MrbsArea;

/**
 * 
 * *
 * <p>
 * Title:会议室预定bean
 * </p>
 * <p>
 * Description:用于作为会议室查询的bean
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
 * @time: 2009-3-22 下午09:44:25
 * 
 */
public class RoomSearchBean {
	/**
	 * 与会人数
	 */
	private int capacity;
	/**
	 * 会议耗时
	 */
	private float timeConsuming;
	/**
	 * 开始小时
	 */
	private int beiginHour;
	/**
	 *开始分钟
	 */
	private int beiginMimi;
	/**
	 * 开始时间是否是必须<br>
	 * 1表示是，0表示否
	 */
	private int isMust;
	/**
	 * 开始时间
	 */
	private Date startDate;

	/**
	 * 結束时间
	 */
	private Date endDate;
	/**
	 * 可选区域列表
	 */
	private List<MrbsArea> areaList;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getBeiginHour() {
		return beiginHour;
	}

	public float getTimeConsuming() {
		return timeConsuming;
	}

	public void setTimeConsuming(float timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	public void setBeiginHour(int beiginHour) {
		this.beiginHour = beiginHour;
	}

	public int getBeiginMimi() {
		return beiginMimi;
	}

	public void setBeiginMimi(int beiginMimi) {
		this.beiginMimi = beiginMimi;
	}

	public int getIsMust() {
		return isMust;
	}

	public void setIsMust(int isMust) {
		this.isMust = isMust;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<MrbsArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<MrbsArea> areaList) {
		this.areaList = areaList;
	}
}
