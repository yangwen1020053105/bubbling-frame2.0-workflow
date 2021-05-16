package com.bubbling.frame.base.utils;

import com.bubbling.frame.entity.TAcUser;

import java.util.*;

/**
 * @title: SessionUtils
 * @Author yangwen
 * @Date: 2021-04-07 21:17
 * @ignore
 */
public class SessionUtils {

    //登录后存放token和user
    public static Map<String, TAcUser> sessionMap=new HashMap<>();
    public static void setUser(String token,TAcUser user){
        sessionMap.put(token,user);
    }
    public static TAcUser getUser(String token){
        return sessionMap.get(token);
    }
    //存放token过期时间
    public static Map<String, Long> sessionTimeOutMap=new HashMap<>();
    public static void setSessionTimeOut(Long timeOut,String token){
        sessionTimeOutMap.put(token,timeOut);
    }
    public static Long getSessionTimeOut(String token){
        return sessionTimeOutMap.get(token);
    }
    //过滤器验证user后将user放入线程变量
    private static ThreadLocal<TAcUser> localUser=new ThreadLocal<>();
    public static void setLocalUser(TAcUser user){
        localUser.set(user);
    }
    /**
     *获取用户
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021-04-09 22:30
     */
    public static TAcUser getUser() throws Exception {
        TAcUser tAcUser = localUser.get();
        if(tAcUser!=null){
            return tAcUser;
        }else{
            if(ApplicationUtils.authUser){
                throw new Exception("未登录");
            }else{
                tAcUser=new TAcUser();
                tAcUser.setId("001");
                tAcUser.setUserName("管理员");
                return tAcUser;
            }
        }
    }
    /**
     *获取用户id
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021-04-09 22:30
     */
    public static String getUserId()  {
        try {
            return getUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *获取用户姓名
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021-04-09 22:30
     */
    public static String getUserName()  {
        try {
            return getUser().getUserName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *验证token有效性
     *@param token token
     *@Return:boolean
     *@Author:dc_yangwen
     *@Date:2021-04-09 22:04
     */
    public static boolean checkToken(String token){
        Long timeOut=SessionUtils.getSessionTimeOut(token);
        if(timeOut!=null&&timeOut>new Date().getTime()){
            //未过有效期
            TAcUser user = SessionUtils.getUser(token);
            if(BaseUtils.isNotNull(user)){
                //user有效
                setLocalUser(user);
                return true;
            }else{
                //user无效
                return false;
            }
        }else{
            //已过有效期
            return false;
        }
    }
}
