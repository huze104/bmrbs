package com.baidu.ite.mrbs.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 
 * *
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:常用日期函数<br>
 * 这里只是写了项目中用到
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
 * @time: 2009-3-3 上午10:43:18
 * 
 */
public final class DateComputorUtil {

	/**
	 * 得到两个文本日期之间的天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 日期之间相隔的天数
	 */
	public static long getDaysBetween(Date startDate, Date endDate) {
		return (endDate.getTime() - startDate.getTime())
				/ (24 * 60 * 60 * 1000);
	}

	/**
	 * 得到两个文本日期之间的小时数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 日期之间相隔的小时数
	 */
	public static double getHoursBetween(Date startDate, Date endDate) {
		return (endDate.getTime() - startDate.getTime()) / (60 * 60 * 1000.0);
	}

	/**
	 * 判断某个日期是否是工作日<br>
	 * 对于工作日日的界定是指周一到周五,对于国家法定节假日不予考虑
	 * 
	 * @param date
	 *            等待判断的日期
	 * @return
	 */
	public static boolean isWorkDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		// release it
		calendar = null;
		if (dayOfWeek == 1 || dayOfWeek == 7) {
			return false;
		}
		return true;

	}

	/**
	 * 得到两个文本日期之间的周
	 * 
	 * @param startTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @return 日期之间相隔的周
	 */
	public static long getWeeksBetween(Date startTime, Date endTime) {
		return (endTime.getTime() - startTime.getTime())
				/ (7 * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获取某个日期下一个周几
	 * 
	 * @param startTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @return 日期之间相隔的周
	 */
	public static Date getNextWeekDay(Date date, int dayOfWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar = null;
		if (dayOfWeek <= currentDayOfWeek) {
			dayOfWeek += 7;
		}
		return DateUtils.addDays(date, dayOfWeek - currentDayOfWeek);
	}
}
