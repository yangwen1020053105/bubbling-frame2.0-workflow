package com.bubbling.frame.base.config;

import com.bubbling.frame.base.filter.UserFilter;
import com.bubbling.frame.base.utils.ApplicationUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @title: 权限拦截器
 * @Author yangwen
 * @Date: 2021-04-07 22:03
 * @ignore
 */
@Configuration
public class AuthConfig  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录验证
        if(ApplicationUtils.authUser){
            registry.addInterceptor(new UserFilter())
                    .addPathPatterns("/**")
                    .excludePathPatterns("/login.do")
                    .excludePathPatterns("/test.do")
                    .excludePathPatterns("/checkToken.do")
                    .excludePathPatterns("/queryRsaPublicKey.do");
        }
    }
}
