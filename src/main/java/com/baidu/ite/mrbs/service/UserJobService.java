package com.baidu.ite.mrbs.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.baidu.ite.mrbs.entity.MrbsUser;

/**
 * 
 * *
 * <p>
 * Title:userJobService
 * </p>
 * <p>
 * Description:用来定时与用户中心做出用戶同步<br>
 * UIC系统提供两种用户数据同步的方式，第一种是全部最新数据（将会放置到一个ftp上）<br>
 * 第二种方式是增量数据同步方式,通过http的方式对外提供服务。当用户请求数据的时候，提供本地数据的版本号，服务端据此返回更新的数据
 * 
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
 * @time: 2009-3-19 下午02:49:15
 * 
 */

public class UserJobService {
	protected final Logger logger = LoggerFactory
			.getLogger(UserJobService.class);

	private HibernateDao<MrbsUser, Long> userDao;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userDao = new HibernateDao<MrbsUser, Long>(sessionFactory,
				MrbsUser.class);
	}

	private String userSynchUrl;

	// @Autowired
	// private UserRemote userServiceClient;

	/**
	 * 与UIC同步用户数据
	 */
	public void synchronizationUserFromUIC() {
		logger.info("开始同步数据");
		//FIXME 这里的获取数据部分还没有写呢
		// 从文件中读取版本号码
		File file = new File(this.getClass().getResource("/").getPath()
				+ "userSynVersion.properties");
		long len = file.length();
		byte[] bytes = new byte[(int) len];
		int r;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(file));
			r = bufferedInputStream.read(bytes);
			if (r != len) {
				logger.error("读取version文件错误！");
				throw new IOException("读取文件不正确");
			}
			bufferedInputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 获取到版本号码
		String version = new String(bytes);
		logger.info("用户同步的version:"+version);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(userSynchUrl);

		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody;
		try {
			responseBody = httpclient.execute(httpget, responseHandler);
			// 拿到responseBody
			logger.debug("获取到数据为：" + responseBody);
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("获取数据错误:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取数据错误:" + e.getMessage());
		}

		version = System.currentTimeMillis() + "";
		BufferedOutputStream bufferedOutputStream;
		try {
			bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(file));
			bufferedOutputStream.write(version.getBytes());
			bufferedOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("写出用户数据版本错误:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("写出用户数据版本错误:" + e.getMessage());
		}

	}

	public String getUserSynchUrl() {
		return userSynchUrl;
	}

	public void setUserSynchUrl(String userSynchUrl) {
		this.userSynchUrl = userSynchUrl;
	}
}
