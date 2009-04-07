package com.baidu.ite.mrbs.entity;

import java.util.LinkedHashSet;
import java.util.List;
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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 角色.
 * 
 * 手写JPA annotation注明ORM关系，尽量使用JPA默认设定. 用@Cache注明Hibernate缓存策略.
 * 
 * 对于会议室预定系统，我改变了表名称，主要是考虑到数据库会与其他系统部署在一起（oracle） added by hongzhi.
 * 
 * @author calvin
 */
@Entity
@Table(name = "mrbs_roles")
@AccessType("field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	/**
	 * 資源
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "mrbs_roles_authorities", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@OrderBy("id")
	private Set<Authority> auths = new LinkedHashSet<Authority>();
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "mrbs_users_roles", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "id") })
	@OrderBy("id")
	private Set<MrbsUser> users = new LinkedHashSet<MrbsUser>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Set<Authority> getAuths() {
		return auths;
	}

	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	@Transient
	public String getAuthNames() throws Exception {
		return ReflectionUtils.fetchElementPropertyToString(auths, "displayName", ", ");
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<Long> getAuthIds() throws Exception {
		return ReflectionUtils.fetchElementPropertyToList(auths, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<MrbsUser> getUsers() {
		return users;
	}

	public void setUsers(Set<MrbsUser> users) {
		this.users = users;
	}

	
}
