package com.bubbling.frame.base.tools;

import java.util.Calendar;
import java.util.Date;

/**
 * @title: DateUtils
 * @Author yangwen
 * @Date: 2021-04-09 21:21
 * @ignore
 */
public class DateUtils {
    /**
     *时间增加小时
     *@param date 时间
     *@param hours 增加的小时数
     *@Return:java.util.Date
     *@Author:dc_yangwen
     *@Date:2021-04-09 21:24
     */
    public static Date dateAddHours(Date date,int hours){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,hours);
        return calendar.getTime();
    }
}
