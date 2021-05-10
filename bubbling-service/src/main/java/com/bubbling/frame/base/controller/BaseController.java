package com.bubbling.frame.base.controller;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.bubbling.frame.base.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

@Controller
public class BaseController {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 该方法用于统一service访问入口
     *
     * @param serviceName @PathVariable：一般我们使用URI template样式映射使用，即url/{param}这种形式，
     *                    也就是一般我们使用的GET，DELETE，PUT方法会使用到的，我们可以获取URL后所跟的参数。
     * @param methodName  @RequestParam：一般我们使用该注解来获取多个参数，一般用于form表单提交 在（）内写入需要获取参数的参数名即可，一般在PUT，POST中比较常用。
     * @param reqMap      @RequestBody：该注解和@RequestParam殊途同归，一般用于content-type是json类型的 我们使用该注解将所有参数转换，在代码部分在一个个取出来，也是目前我使用到最多的注解来获取参数（因为前端不愿意一个一个接口的调试）
     * @return map
     */
    @PostMapping("/proxyService/{serviceName}/{methodName}")
    @ResponseBody
    public ResponseBean postService(HttpServletRequest request, @PathVariable String serviceName, @PathVariable String methodName, @RequestBody(required = false) Map<String, Object> reqMap) throws Throwable {
    	Map<String, Object> map = new HashMap<>(16);
        reqMap = reqMap == null ? new HashMap<>(16) : reqMap;
        Object result = null;
        try {
            //java反射类对象
            //通过spring上下文获取Service层对象
            Object object = applicationContext.getBean(serviceName);
            Class<?> cls = object.getClass();
            if (cls.isInstance(object)) {
                Method method = cls.getDeclaredMethod(methodName, Map.class);
                result = method.invoke(object, reqMap);
            }
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }

        if (result instanceof ResponseBean) {
            return (ResponseBean) result;
        } else {
            JSONObject json = JSONObject.parseObject((String) result);
            for (Map.Entry<String, Object> entry : json.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            return ResponseBean.rtn((Integer) map.get("rtnCode"), (String) map.get("rtnMsg"), map.get("data"));
        }
    }
    /**
     *测试
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-04-10 11:12
     */
    @GetMapping("/test.do")
    @ResponseBody
    public ResponseBean test(){
        return ResponseBean.success();
    }
}
