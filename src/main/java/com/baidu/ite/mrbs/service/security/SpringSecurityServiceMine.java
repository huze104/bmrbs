package com.baidu.ite.mrbs.service.security;

import org.granite.messaging.service.security.SpringSecurityService;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

/**
 * *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:重新书写其获取权限的方法，主要原因是，在百度会议室预定系统中使用的cas负责登陆，而不是使用flex端基于数据库的验证方式<br>
 * 即父类方法不能够获取到spring security中的authentication(Bouiaw)是从session中获取的<br>
 * 对于是否能够获取到正确的authentiacation影响最大的是granite-config中的对于整个service方法的控制，具体可以参见父类的Authentication方法
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
 * @time: 2009-3-25 下午02:22:55
 */
public class SpringSecurityServiceMine extends SpringSecurityService {
	/**
	 * 重写一下,增加在holder中获取数据的途径
	 */
	protected Authentication getAuthentication() {
		Authentication authentication = super.getAuthentication();
		if (authentication == null) {
			authentication = SecurityContextHolder.getContext()
					.getAuthentication();
		}
		return authentication;
	}
}
