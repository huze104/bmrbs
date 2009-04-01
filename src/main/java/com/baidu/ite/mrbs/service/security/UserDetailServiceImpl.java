package com.baidu.ite.mrbs.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baidu.ite.mrbs.entity.Authority;
import com.baidu.ite.mrbs.entity.MrbsUser;
import com.baidu.ite.mrbs.entity.Role;
import com.baidu.ite.mrbs.service.MrbsUserManager;

/**
 * 实现Acegi的UserDetailsService接口,获取用户Detail信息.
 * 
 * @author calvin
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private MrbsUserManager userManager;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		// 这里根据用户名，取得用户信息，然后返回userdetail对象，acegi会根据userdetail来认证
		MrbsUser user = userManager.getUserByLoginName(userName);
		if (user == null)
			throw new UsernameNotFoundException(userName + " 不存在");

		List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuths()) {
				authsList.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
        //嘿嘿，手工在这里给每一个登陆用户加入一个登陆权限
		authsList.add(new GrantedAuthorityImpl("AUTH_LOGIN"));
		// 设定enabled, accountNonExpired,credentialsNonExpired,accountNonLocked等
		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				user.getLoginName(), user.getPassword()==null?"":user.getPassword(),
				user.getStatus() == 1 ? false : true, true, true, true,
				authsList.toArray(new GrantedAuthority[authsList.size()]));

		return userdetail;
	}

	@Required
	public void setUserManager(MrbsUserManager userManager) {
		this.userManager = userManager;
	}

}
