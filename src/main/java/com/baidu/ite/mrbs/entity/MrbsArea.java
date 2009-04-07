/**
 * BMRBS - BaiDu meeting room book system -百度会议室预定系统<br>
 * http://www.baidu.com
 * <p>
 * 遵循GNU协议 <br>
 * 在此基础上做出的修改都需要保持本声明。另外，基于此做出的修改必须作为开源！<br>
 * <p>
 */
package com.baidu.ite.mrbs.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * <p>
 * Title:MrbsArea.java
 * </p>
 * <p>
 * Description:区域类
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
@Table(name = "mrbs_area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AccessType("field")
public class MrbsArea {

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
	@Column(name = "area_name", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private java.lang.String areaName;

	/**
	 *管理员，这里放置的是该区域的前台或者行政人员
	 */
	@Column(name = "linkman", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	private java.lang.String linkman;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "mrbsArea")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	private Set<MrbsRoom> mrbsRooms = new LinkedHashSet<MrbsRoom>(17);

	/**
	 * 会议室公告
	 */
	private String descn;
	/**
	 * short descn
	 */
	private String shortDescn;

	// 属性 END

	public MrbsArea() {
	}

	public MrbsArea(java.lang.Long id) {
		this.id = id;
	}

	/**
	 * 提供设置主键（id）接口
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 *提供获取主键（id）接口
	 */
	public void setId(java.lang.Long value) {
		this.id = value;
	}

	/**
	 * 提供设置名称（areaName）接口
	 */
	public java.lang.String getAreaName() {
		return this.areaName;
	}

	/**
	 *提供获取名称（areaName）接口
	 */
	public void setAreaName(java.lang.String value) {
		this.areaName = value;
	}

	public void setMrbsRooms(Set<MrbsRoom> mrbsRooms) {
		this.mrbsRooms = mrbsRooms;
	}

	public Set<MrbsRoom> getMrbsRooms() {
		return mrbsRooms;
	}

	public java.lang.String getLinkman() {
		return linkman;
	}

	public void setLinkman(java.lang.String linkman) {
		this.linkman = linkman;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getShortDescn() {
		return shortDescn;
	}

	public void setShortDescn(String shortDescn) {
		this.shortDescn = shortDescn;
	}

}
