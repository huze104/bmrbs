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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * Title:MrbsSchedule.java
 * </p>
 * <p>
 * Description:会议调度表， 用于记录用户会议申请记录， 并根据会议记录生成相关的会议详细记录
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
 * @time: 2009-02-28 22-51-14
 * 
 */

@Entity
@Table(name = "mrbs_schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AccessType("field")
public class MrbsSchedule {

	// 属性 START
	/**
	 *系统主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private java.lang.Long id;
	/**
	 *开始时间
	 */
	@Column(name = "start_time", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	private Date startTime;
	/**
	 *结束时间
	 */
	@Column(name = "end_time", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	private Date endTime;
	/**
	 *添加者
	 */
	@Column(name = "create_by", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
	private java.lang.String createBy;
	/**
	 *主持人
	 */
	@Column(name = "preside", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
	private java.lang.String preside;
	/**
	 *会议标题 会议标题
	 */
	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	private String title;

	/**
	 *会议内容说明
	 */
	@Column(name = "description", unique = false, nullable = false, insertable = true, updatable = true, length = 65535)
	private java.lang.String description;
	/**
	 *与会人数
	 */
	@Column(name = "num", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	private int num = 0;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "repeat_id", nullable = false, insertable = true, updatable = true)
	private MrbsRepeat mrbsRepeat;

	/**
	 * 所订的会议室
	 */
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false, insertable = true, updatable = true)
	private MrbsRoom mrbsRoom;

	/**
	 * todo: 参与此会议的用户
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	@JoinTable(name = "mrbs_schedule_user", joinColumns = { @JoinColumn(name = "scheduleid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<MrbsUser> mrbsUsers = new HashSet<MrbsUser>(0);

	// 属性 END

	public MrbsSchedule(MrbsRepeat repeat) {
		this.setCreateBy(repeat.getCreateBy());
		this.setDescription(repeat.getDescription());
		this.setMrbsRepeat(repeat);
		this.setMrbsRoom(repeat.getMrbsRoom());
		// FIXME 这个参数暂时不能够提供
		// schedule.setNum();
		// 预定者
		this.setPreside(repeat.getOrderman());
	}

	public MrbsSchedule() {
	}
/**
 * 
 * @param repeat
 * @param tempStartTime
 * @param tempEndTime
 */
	public MrbsSchedule(MrbsRoom room, Date tempStartTime, Date tempEndTime) {
		this.setMrbsRoom(room);
		this.setStartTime(tempStartTime);
		this.setEndTime(tempEndTime);
	}

	/**
	 * 基于repeat构建
	 * @param repeat
	 * @param tempStartTime
	 * @param tempEndTime
	 */
	public MrbsSchedule(MrbsRepeat repeat, Date tempStartTime, Date tempEndTime) {
		this.setCreateBy(repeat.getCreateBy());
		this.setDescription(repeat.getDescription());
		this.setMrbsRepeat(repeat);
		this.setMrbsRoom(repeat.getMrbsRoom());
		// FIXME 这个参数暂时不能够提供
		// schedule.setNum();
		// 预定者
		this.setPreside(repeat.getOrderman());
		this.setStartTime(tempStartTime);
		this.setEndTime(tempEndTime);
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
	 * 提供设置预定者，预定者主持会议（preside）接口
	 */
	public java.lang.String getPreside() {
		return this.preside;
	}

	/**
	 *提供获取预定者，预定者主持会议（preside）接口
	 */
	public void setPreside(java.lang.String value) {
		this.preside = value;
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
	 * 提供设置与会人数（num）接口
	 */
	public int getNum() {
		return this.num;
	}

	/**
	 *提供获取与会人数（num）接口
	 */
	public void setNum(int value) {
		this.num = value;
	}

	public void setMrbsRepeat(MrbsRepeat mrbsRepeat) {
		this.mrbsRepeat = mrbsRepeat;
	}

	public MrbsRepeat getMrbsRepeat() {
		return mrbsRepeat;
	}

	public void setMrbsRoom(MrbsRoom mrbsRoom) {
		this.mrbsRoom = mrbsRoom;
	}

	public MrbsRoom getMrbsRoom() {
		return mrbsRoom;
	}

	public Set<MrbsUser> getMrbsUsers() {
		return mrbsUsers;
	}

	public void setMrbsUsers(Set<MrbsUser> mrbsUsers) {
		this.mrbsUsers = mrbsUsers;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
