package com.baidu.ite.mrbs.service.security;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.baidu.ite.mrbs.entity.MrbsUser;
import com.baidu.ite.mrbs.service.MrbsUserManager;

/**
 * 
 * *
 * <p>
 * Title:LoginServiceImpl
 * </p>
 * <p>
 * Description:供Flex端进行用户认证的service，具体的认证工作已经由spring security做了，这里的主要目的是<br>
 * 获取到具体的用户，返还给client端，供client端使用
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
 * @time: 2009-3-15 下午09:53:42
 * 
 */
@Service
public class LoginService {

	private MrbsUserManager userManager;

	/**
	 * 登陆认证和权限验证部分已经有spring security完成，这里需要将当前用户返回，供客户端使用
	 */
	public MrbsUser checkLogin() {
		MrbsUser user = null;
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (obj instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) obj;
			user = userManager.getUserByLoginName(userDetails.getUsername());
		}
		return user;
	}

	@Required
	public void setUserManager(MrbsUserManager userManager) {
		this.userManager = userManager;
	}

}
