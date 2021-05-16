package com.bubbling.frame.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bubbling.frame.entity.TAcUser;
import com.bubbling.frame.dao.TAcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbling.frame.base.service.impl.BaseService;
import com.bubbling.frame.service.IUserService;
@Service
public class UserService extends BaseService implements IUserService {

	@Autowired
	private TAcUserMapper tAcUserMapper;
	/**
	 *获取user
	 *@param userName
	 *@Return:com.bubbling.frame.entity.TAcUser
	 *@Author:dc_yangwen
	 *@Date:2021-04-05 22:39
	 */
	@Override
	public TAcUser quertUser(String userName) throws Exception {
		QueryWrapper queryWrapper=new QueryWrapper();
		queryWrapper.eq("login_name",userName);
		List<TAcUser> users=tAcUserMapper.selectList(queryWrapper);
		if (users.size()!=0) {
			return users.get(0);
		}else {
			return null;
		}
		
	}
	/**
	 *修改用户信息
	 *@param tAcUser
	 *@Return:void
	 *@Author:dc_yangwen
	 *@Date:2021-05-09 9:10
	 */
	@Override
	public void updateUser(TAcUser tAcUser) {
		tAcUserMapper.updateById(tAcUser);
	}


}
