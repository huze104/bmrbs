/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * Title:MrbsRepeat.java
 * </p>
 * <p>
 * //todo:这个类我觉得有问题。逻辑不清。我不敢动。
 * Description:会议申请情况
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
 * @time: 2009-02-28 22-51-13
 * 
 */

@Entity
@Table(name = "mrbs_repeat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AccessType("field")
public class MrbsRepeat {

	// 属性 START
	/**
	 *系统主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private java.lang.Long id;
	/**
	 *开始时间,以小时计
	 */
	@Column(name = "start_hour", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	private java.lang.String startHour;
	/**
	 *结束时间
	 */
	@Column(name = "end_hour", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	private java.lang.String endHour;
	/**
	 *开始分钟
	 */
	@Column(name = "start_mini", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private java.lang.String startMini;
	/**
	 *结束分钟
	 */
	@Column(name = "end_mini", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	private java.lang.String endMini;
	/**
	 *更新时间
	 */
	@Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	private java.sql.Timestamp updatetime;
	/**
	 *添加者
	 */
	@Column(name = "create_by", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
	private java.lang.String createBy;
	/**
	 *预定者,存个名字而已
	 */
	@Column(name = "orderman", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	private java.lang.String orderman;
	/**
	 *重复情况<br>
	 *0代表不重复，1代表每天，2代表每周，3代表隔开n周，4代表每月
	 */
	@Column(name = "rep_opt", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	private int repOpt = 0;
	/**
	 *会议室描述
	 */
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	private java.lang.String description;
	/**
	 *开始日期
	 */
	@Column(name = "start_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private Date startDate;
	/**
	 *结束日期
	 */
	@Column(name = "end_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private Date endDate;
	/**
	 *是否整天 0表示否，1表示是
	 */
	@Column(name = "allday", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private int allday = 0;
	/**
	 *重复星期<br>
	 * <tt>2</tt> = Monday, <tt>3</tt> = Tuesday, <tt>4</tt> = Wednesday,
	 * <tt>5</tt> = Thursday, <tt>6</tt> = Friday
	 */
	@Column(name = "repeat_week_day", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private int repeatWeekDay;
	/**
	 *隔开几周
	 */
	@Column(name = "week_span", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private int weekSpan;
	/**
	 *重复日
	 */
	@Column(name = "repeat_day", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private int repeatDay;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false, insertable = true, updatable = false)
	private MrbsRoom mrbsRoom;

	// 让repeat一方来维护schedule的数据
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "repeat_id")
	private Set<MrbsSchedule> mrbsSchedules = new HashSet<MrbsSchedule>(0);

	// 属性 END

	public MrbsRepeat() {
	}

	public MrbsRepeat(java.lang.Long id) {
		this.id = id;
	}

	/**
	 * 提供设置FIXME 填写该字段中文描述（id）接口
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 *提供获取FIXME 填写该字段中文描述（id）接口
	 */
	public void setId(java.lang.Long value) {
		this.id = value;
	}

	/**
	 * 提供设置开始时间,以小时计（startHour）接口
	 */
	public java.lang.String getStartHour() {
		return this.startHour;
	}

	/**
	 *提供获取开始时间,以小时计（startHour）接口
	 */
	public void setStartHour(java.lang.String value) {
		this.startHour = value;
	}

	/**
	 * 提供设置结束时间（endHour）接口
	 */
	public java.lang.String getEndHour() {
		return this.endHour;
	}

	/**
	 *提供获取结束时间（endHour）接口
	 */
	public void setEndHour(java.lang.String value) {
		this.endHour = value;
	}

	/**
	 * 提供设置开始分钟（startMini）接口
	 */
	public java.lang.String getStartMini() {
		return this.startMini;
	}

	/**
	 *提供获取开始分钟（startMini）接口
	 */
	public void setStartMini(java.lang.String value) {
		this.startMini = value;
	}

	/**
	 * 提供设置结束分钟（endMini）接口
	 */
	public java.lang.String getEndMini() {
		return this.endMini;
	}

	/**
	 *提供获取结束分钟（endMini）接口
	 */
	public void setEndMini(java.lang.String value) {
		this.endMini = value;
	}

	/**
	 * 提供设置FIXME 填写该字段中文描述（updatetime）接口
	 */
	public java.sql.Timestamp getUpdatetime() {
		return this.updatetime;
	}

	/**
	 *提供获取FIXME 填写该字段中文描述（updatetime）接口
	 */
	public void setUpdatetime(java.sql.Timestamp value) {
		this.updatetime = value;
	}

	/**
	 * 提供设置添加者（createBy）接口
	 */
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	/**
	 *提供获取添加者（createBy）接口
	 */
	public void setCreateBy(java.lang.String value) {
		this.createBy = value;
	}

	/**
	 * 提供设置预定者,存个名字而已（orderman）接口
	 */
	public java.lang.String getOrderman() {
		return this.orderman;
	}

	/**
	 *提供获取预定者,存个名字而已（orderman）接口
	 */
	public void setOrderman(java.lang.String value) {
		this.orderman = value;
	}

	/**
	 * 提供设置重复情况（repOpt）接口
	 */
	public int getRepOpt() {
		return this.repOpt;
	}

	/**
	 *提供获取重复情况（repOpt）接口
	 */
	public void setRepOpt(int value) {
		this.repOpt = value;
	}

	/**
	 * 提供设置会议室描述（description）接口
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 *提供获取会议室描述（description）接口
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}

	/**
	 * 提供设置开始日期（startDate）接口
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 *提供获取开始日期（startDate）接口
	 */
	public void setStartDate(Date value) {
		this.startDate = value;
	}

	/**
	 * 提供设置结束日期（endDate）接口
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 *提供获取结束日期（endDate）接口
	 */
	public void setEndDate(Date value) {
		this.endDate = value;
	}

	/**
	 * 提供设置是否整天（allday）接口
	 */
	public int getAllday() {
		return this.allday;
	}

	/**
	 *提供获取是否整天（allday）接口
	 */
	public void setAllday(int value) {
		this.allday = value;
	}

	/**
	 * 提供设置重复星期（repeatWeekDay）接口
	 */
	public int getRepeatWeekDay() {
		return this.repeatWeekDay;
	}

	/**
	 *提供获取重复星期（repeatWeekDay）接口
	 */
	public void setRepeatWeekDay(int value) {
		this.repeatWeekDay = value;
	}

	/**
	 * 提供设置隔开几周（weekSpan）接口
	 */
	public int getWeekSpan() {
		return this.weekSpan;
	}

	/**
	 *提供获取隔开几周（weekSpan）接口
	 */
	public void setWeekSpan(int value) {
		this.weekSpan = value;
	}

	/**
	 * 提供设置重复日（repeatDay）接口
	 */
	public int getRepeatDay() {
		return this.repeatDay;
	}

	/**
	 *提供获取重复日（repeatDay）接口
	 */
	public void setRepeatDay(int value) {
		this.repeatDay = value;
	}

	public void setMrbsRoom(MrbsRoom mrbsRoom) {
		this.mrbsRoom = mrbsRoom;
	}

	public MrbsRoom getMrbsRoom() {
		return mrbsRoom;
	}

	public void setMrbsSchedules(Set<MrbsSchedule> mrbsSchedules) {
		this.mrbsSchedules = mrbsSchedules;
	}

	public Set<MrbsSchedule> getMrbsSchedules() {
		return mrbsSchedules;
	}

}
