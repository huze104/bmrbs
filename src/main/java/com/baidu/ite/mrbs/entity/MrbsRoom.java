/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * Title:MrbsRoom.java
 * </p>
 * <p>
 * Description:会议室情况
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
@Table(name = "mrbs_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AccessType("field")
public class MrbsRoom {

	// 属性 START
	/**
	 *系统主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private java.lang.Long id;
	/**
	 *名称
	 */
	@Column(name = "room_name", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	private java.lang.String roomName;
	/**
	 *简介
	 */
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	private java.lang.String description;

	/**
	 * todo:加入排序 排序
	 */
	private String sortIndex;
	/**
	 * todo:加入实景图 实景图
	 */
	private String virtualMap;
	/**
	 *容纳人数
	 */
	@Column(name = "capacity", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private java.lang.Long capacity;

	/**
	 * todo: 会议启用情况 考虑到会议室要装修情况
	 */
	@Column(name = "status")
	private String status;
	/**
	 *会议室管理员联系方式
	 */
	@Column(name = "room_admin_email", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	private java.lang.String roomAdminEmail;
	/**
	 * 会议服务（管理）人员
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "mrbs_room_service_user", joinColumns = { @JoinColumn(name = "roomid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<MrbsUser> mrbsUsers = new HashSet<MrbsUser>(0);

	/**
	 * 所在区域
	 */
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id", nullable = false, insertable = true, updatable = true)
	private MrbsArea mrbsArea;

	/**
     *
     */
	@OneToMany(mappedBy = "mrbsRoom")
	private Set<MrbsSchedule> mrbsSchedules = new HashSet<MrbsSchedule>(0);

	@OneToMany(mappedBy = "mrbsRoom")
	private Set<MrbsRepeat> mrbsRepeats = new HashSet<MrbsRepeat>(0);

	// 属性 END

	public MrbsRoom() {
	}

	public MrbsRoom(java.lang.Long id) {
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
	 * 提供设置名称（roomName）接口
	 */
	public java.lang.String getRoomName() {
		return this.roomName;
	}

	/**
	 *提供获取名称（roomName）接口
	 */
	public void setRoomName(java.lang.String value) {
		this.roomName = value;
	}

	/**
	 * 提供设置简介（description）接口
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 *提供获取简介（description）接口
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}

	/**
	 * 提供设置容纳人数（capacity）接口
	 */
	public java.lang.Long getCapacity() {
		return this.capacity;
	}

	/**
	 *提供获取容纳人数（capacity）接口
	 */
	public void setCapacity(java.lang.Long value) {
		this.capacity = value;
	}

	/**
	 * 提供设置联系方式（roomAdminEmail）接口
	 */
	public java.lang.String getRoomAdminEmail() {
		return this.roomAdminEmail;
	}

	/**
	 *提供获取联系方式（roomAdminEmail）接口
	 */
	public void setRoomAdminEmail(java.lang.String value) {
		this.roomAdminEmail = value;
	}

	public void setMrbsArea(MrbsArea mrbsArea) {
		this.mrbsArea = mrbsArea;
	}

	public MrbsArea getMrbsArea() {
		return mrbsArea;
	}

	public void setMrbsSchedules(Set<MrbsSchedule> mrbsSchedules) {
		this.mrbsSchedules = mrbsSchedules;
	}

	public Set<MrbsSchedule> getMrbsSchedules() {
		return mrbsSchedules;
	}

	public void setMrbsRepeats(Set<MrbsRepeat> mrbsRepeats) {
		this.mrbsRepeats = mrbsRepeats;
	}

	public Set<MrbsRepeat> getMrbsRepeats() {
		return mrbsRepeats;
	}

	public String getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getVirtualMap() {
		return virtualMap;
	}

	public void setVirtualMap(String virtualMap) {
		this.virtualMap = virtualMap;
	}

	public Set<MrbsUser> getMrbsUsers() {
		return mrbsUsers;
	}

	public void setMrbsUsers(Set<MrbsUser> mrbsUsers) {
		this.mrbsUsers = mrbsUsers;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
