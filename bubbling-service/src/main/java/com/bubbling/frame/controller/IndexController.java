package com.bubbling.frame.controller;

import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.constants.SrvConstants;
import com.bubbling.frame.base.tools.*;
import com.bubbling.frame.entity.TAcUser;
import com.bubbling.frame.service.IIndexService;
import com.bubbling.frame.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: 登录及主页
 * @Author yangwen
 * @Date: 2021-04-11 12:47
 * @ignore
 */
@Slf4j
@RestController
public class IndexController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IIndexService indexService;
    /**
     *登录方法
     *@param reqMap userName password
     *@param httpServletRequest
     *@param httpServletResponse
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-04-09 21:19
     */
    @PostMapping("/login.do")
    public ResponseBean login(@RequestBody Map<String, Object> reqMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String userName= (String) reqMap.get("userName");
        String password= (String) reqMap.get("password");
        String publicKey= (String) reqMap.get("publicKey");
        if(RSAencryUtils.rsaTimeOutMap.get(publicKey)>new Date().getTime()){
            //未过有效期
            String privateKey = RSAencryUtils.rsaMap.get(publicKey);
            //使用私钥解密
            userName=RSAencryUtils.decrypt(userName,privateKey);
            //使用私钥解密并md5加密
            password= BaseUtils.getMD5String(RSAencryUtils.decrypt(password,privateKey));
            TAcUser tAcUser = userService.quertUser(userName);
            if(password.equals(tAcUser.getPassword())){
                //登录成功
                String token=BaseUtils.getUUID();
                SessionUtils.setUser(token,tAcUser);
                SessionUtils.setSessionTimeOut(DateUtils.dateAddHours(new Date(),3).getTime(),token);
                Map<String,String> rtn=new HashMap<>();
                rtn.put("token",token);
                rtn.put("userName",tAcUser.getUserName());
                return ResponseBean.success(rtn);
            }else{
                //密码不正确
                return ResponseBean.error("账号或密码错误");
            }
        }else{
            return ResponseBean.error("公钥已过有效期，请联系管理员！");
        }

    }
    @GetMapping("/loginOut.do")
    public ResponseBean loginOut(String token){
        SessionUtils.setSessionTimeOut(new Date().getTime(),token);
        return ResponseBean.success();
    }

    @PostMapping("/updatePassword.do")
    public ResponseBean updatePassword(@RequestBody Map<String,Object> map){
        String token= (String) map.get("token");
        String oldPassword=(String) map.get("oldPassword");
        String newPassword=(String) map.get("newPassword");
        TAcUser tAcUser=SessionUtils.getUser(token);
        //验证登录信息
        if(SessionUtils.checkToken(token)&&BaseUtils.isNotNull(tAcUser)){
            //password是否相等
            if(tAcUser.getPassword().equals(BaseUtils.getMD5String(oldPassword))){
                tAcUser.setPassword(BaseUtils.getMD5String(newPassword));
                userService.updateUser(tAcUser);
            }else{
                return ResponseBean.error("旧密码不正确，请重新输入！");
            }
        }else {
            return ResponseBean.error("登录信息无效，请重新登录！");
        }
        return ResponseBean.success();
    }
    /**
     *验证token有效性
     *@param token
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-04-09 22:21
     */
    @GetMapping("/checkToken.do")
    public ResponseBean checkToken(String token){
        if(!ApplicationUtils.authUser){
            return ResponseBean.success();
        }
        if(SessionUtils.checkToken(token)){
            return ResponseBean.success();
        }else{
            return ResponseBean.error(SrvConstants.RTN_CODE_NO_LOGIN,"未登录");
        }

    }
    /**
     *获取公钥
     *@param time http延迟时间
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-04-10 11:12
     */
    @GetMapping("/queryRsaPublicKey.do")
    public ResponseBean queryRsaPublicKey(long time,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  {
        String[] keys = RSAencryUtils.genKeyPair();
        String publicKey=keys[0];
        String privateKey=keys[1];
        RSAencryUtils.rsaMap.put(publicKey,privateKey);
        //过期时间(http响应+获取秘钥100ms)*20
        RSAencryUtils.rsaTimeOutMap.put(publicKey,(new Date().getTime()+time+100)*20);
        return ResponseBean.success(publicKey);
    }
    /**
     *获取菜单树
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-04-11 13:09
     */
    @GetMapping("/queryFuncTree.do")
    public ResponseBean queryFuncTree() throws Exception {
        return ResponseBean.success(indexService.queryFuncTree());
    }
}
