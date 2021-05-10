package com.bubbling.frame.base.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @title: ApplicationUtils
 * @Author yangwen
 * @Date: 2021-04-11 13:20
 * @ignore
 */
@Component
public class ApplicationUtils {
    //是否开启user认证
    public static boolean authUser;
    @Value("${auth.user}")
    public void setAuthUser(String val){
        if("true".equals(val)){
            authUser=true;
        }else{
            authUser=false;
        }
    }
}
