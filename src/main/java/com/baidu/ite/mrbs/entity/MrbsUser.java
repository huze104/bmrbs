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
import java.util.LinkedHashSet;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * Title:MrbsUser.java
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
 * @time: 2009-02-28 22-51-17
 * 
 */

@Entity
@Table(name = "mrbs_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AccessType("field")
public class MrbsUser {

	// 属性 START
	/**
	 *系统主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private java.lang.Long id;
	/**
	 *登陆名
	 */

	@Column(name = "login_name", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	private java.lang.String loginName;
	
	/**
	 * 用户加入是否激活状态
	 */
	// 当中等于1表示失效，0表示有效（默認）
	private Integer status=0;

	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户选择的flex界面主题方案
	 */
	@Column(name="view_choice")
	private String viewChoice;
	/**
	 * 用户归属的角色
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "mrbs_users_roles", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@OrderBy("id")
	private Set<Role> roles = new LinkedHashSet<Role>();
	
	/**
	 * 用户选择的pod
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "mrbs_user_pod", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "pod_id") })
	@OrderBy("id")
	private Set<Pod> pods = new LinkedHashSet<Pod>();
	
	/**
	 * 判断当前用户是否是行政部
	 */
	private boolean assistant=false;
	/**
	 *名字
	 */

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	private java.lang.String name;
	/**
	 *email
	 */

	@Column(name = "email", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	private java.lang.String email;
	/**
	 *分机
	 */

	@Column(name = "tel", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	private java.lang.String tel;
	/**
	 *百度hi
	 */

	@Column(name = "hi", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	private java.lang.String hi;
	/**
	 *部门
	 */

	@Column(name = "department", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	private java.lang.String department;

	/**
	 *参见会议
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "mrbs_schedule_user", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "scheduleid") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<MrbsSchedule> mrbsSchedules = new HashSet<MrbsSchedule>(0);

	// 属性 END

	public MrbsUser() {
	}

	public MrbsUser(java.lang.Long id) {
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
	 * 提供设置登陆名（loginName）接口
	 */
	public java.lang.String getLoginName() {
		return this.loginName;
	}

	/**
	 *提供获取登陆名（loginName）接口
	 */
	public void setLoginName(java.lang.String value) {
		this.loginName = value;
	}

	/**
	 * 提供设置名字（name）接口
	 */
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 *提供获取名字（name）接口
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}

	/**
	 * 提供设置email（email）接口
	 */
	public java.lang.String getEmail() {
		return this.email;
	}

	/**
	 *提供获取email（email）接口
	 */
	public void setEmail(java.lang.String value) {
		this.email = value;
	}

	/**
	 * 提供设置分机（tel）接口
	 */
	public java.lang.String getTel() {
		return this.tel;
	}

	/**
	 *提供获取分机（tel）接口
	 */
	public void setTel(java.lang.String value) {
		this.tel = value;
	}

	/**
	 * 提供设置百度hi（hi）接口
	 */
	public java.lang.String getHi() {
		return this.hi;
	}

	/**
	 *提供获取百度hi（hi）接口
	 */
	public void setHi(java.lang.String value) {
		this.hi = value;
	}

	/**
	 * 提供设置部门（department）接口
	 */
	public java.lang.String getDepartment() {
		return this.department;
	}

	/**
	 *提供获取部门（department）接口
	 */
	public void setDepartment(java.lang.String value) {
		this.department = value;
	}

	public Set<MrbsSchedule> getMrbsSchedules() {
		return mrbsSchedules;
	}

	public void setMrbsSchedules(Set<MrbsSchedule> mrbsSchedules) {
		this.mrbsSchedules = mrbsSchedules;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getViewChoice() {
		return viewChoice;
	}

	public void setViewChoice(String viewChoice) {
		this.viewChoice = viewChoice;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Pod> getPods() {
		return pods;
	}

	public void setPods(Set<Pod> pods) {
		this.pods = pods;
	}

	public boolean isAssistant() {
		return assistant;
	}

	public void setAssistant(boolean assistant) {
		this.assistant = assistant;
	}

}
