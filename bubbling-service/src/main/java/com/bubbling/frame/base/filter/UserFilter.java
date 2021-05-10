package com.bubbling.frame.base.filter;

import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.constants.SrvConstants;
import com.bubbling.frame.base.tools.BaseUtils;
import com.bubbling.frame.base.tools.SessionUtils;
import com.bubbling.frame.entity.TAcUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @title: UserFilter
 * @Author yangwen
 * @Date: 2021-04-07 21:39
 * @ignore
 */
public class UserFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    if(SessionUtils.checkToken(cookie.getValue())){
                        return true;
                    }else{
                        response.getWriter().print("1100");
                        return false;
                    }
                }
            }
        }
        response.getWriter().print("1100");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
