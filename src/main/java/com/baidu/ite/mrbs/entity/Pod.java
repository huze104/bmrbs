package com.baidu.ite.mrbs.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
 * @author hongzhi lanfanss@126.com <br>
 *         用来表示在flex界面方案中，每一个能够纳入到podmanager管理的pod<br>
 *         目前只是考虑将所有的pod放置到一个manager中管理<br>
 * 
 */
@Entity
@Table(name = "mrbs_pod")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AccessType("field")
public class Pod {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * pod名称
	 */
	private String name;
	/**
	 * 该pod对应的组件类路径，供动态的加入到manager中
	 */
	private String componentClass;
	/**
	 * manttomany的映射
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "mrbs_user_pod", joinColumns = { @JoinColumn(name = "pod_id") }, inverseJoinColumns = { @JoinColumn(name = "id") })
	@OrderBy("id")
	private Set<MrbsUser> users = new LinkedHashSet<MrbsUser>();

	public Set<MrbsUser> getUsers() {
		return users;
	}

	public void setUsers(Set<MrbsUser> users) {
		this.users = users;
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponentClass() {
		return componentClass;
	}

	public void setComponentClass(String componentClass) {
		this.componentClass = componentClass;
	}

}