package com.bubbling.frame.service;

import com.bubbling.frame.entity.TAcUser;
import com.bubbling.frame.entity.TAcUserOrg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

	/**
	 * 获取用户
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午7:58:32
	 */
	TAcUser quertUser(String userName) throws Exception;
	/**
	 *修改用户信息
	 *@param tAcUser
	 *@Return:void
	 *@Author:dc_yangwen
	 *@Date:2021-05-09 9:10
	 */
    void updateUser(TAcUser tAcUser);
	/**
	 *查询所有的用户及部门
	 *@Return:java.util.List<com.bubbling.frame.entity.TAcUserOrg>
	 *@Author:dc_yangwen
	 *@Date:2021-05-23 19:02
	 */
    List<TAcUserOrg> queryAllUserAndOrg();
}
