package com.bubbling.frame.service;

import java.util.Map;

import com.bubbling.frame.base.bean.ResponseBean;

public interface IFrameService {

	/**
	 * 获取菜单树
	 * @author         dc_yangwen
	 * @Date           2019年12月21日 下午9:56:40
	 */
	ResponseBean getFuncList(Map<String, Object> map) throws Exception;
	/**
	 * 保存菜单树
	 * @author         dc_yangwen
	 * @Date           2019年12月21日 下午9:57:33
	 */
	ResponseBean saveOrUpdateFunc(Map<String, Object> map) throws Exception;
	/**
	 *删除树
	 *@param map TAcFunc
	 *@Return:com.bubbling.frame.base.bean.ResponseBean
	 *@Author:dc_yangwen
	 *@Date:2021-05-01 15:37
	 */
	ResponseBean delFunc(Map<String, Object> map) throws Exception;

	/**
	 * 获取部门树
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean getOrgList(Map<String, Object> map) throws Exception;
	/**
	 *新增或修改部门
	 *@param map
	 *@Return:com.bubbling.frame.base.bean.ResponseBean
	 *@Author:dc_yangwen
	 *@Date:2021-04-17 18:46
	 */
    ResponseBean saveOrUpdateOrg(Map<String, Object> map) throws Exception;

    ResponseBean delOrg(Map<String, Object> map) throws Exception;

    /**
	 * 根据部门获取用户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean getUserByOrg(Map<String, Object> map) throws Exception;
	/**
	 * 根据部门获取角色
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean getRoleByOrg(Map<String, Object> map) throws Exception;
	/**
	 * 保存用户
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午7:58:32
	 */
	ResponseBean saveOrUpdateUser(Map<String, Object> map) throws Exception;
	/**
	 *删除user
	 *@param map id
	 *@Return:com.bubbling.frame.base.bean.ResponseBean
	 *@Author:dc_yangwen
	 *@Date:2021-04-24 12:34
	 */
	ResponseBean delUser(Map<String, Object> map) throws Exception;
	/**
	 * 保存角色
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean saveOrUpdateRole(Map<String, Object> map) throws Exception;
	/*
	 * 类描述: 删除
	 * @Author: dc_yangwen
	 * @Date: 2021/5/4 18:57
	 */
    ResponseBean delRole(Map<String, Object> map) throws Exception;

    /**
	 * 查询角色对应分菜单
	 * @param map
	 * @author         dc_yangwen
	 * @Date           2019年12月15日 下午7:58:32
	 * @throws Exception
	 */
	ResponseBean queryFuncByRole(Map<String, Object> map) throws Exception;
	/**
	 * 设置权限
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean saveRoleFunc(Map<String, Object> map) throws Exception;
	/**
	 * 获取角色
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean queryRoleByOrg(Map<String, Object> map) throws Exception;
	/**
	 * 保存授权
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean saveUserRole(Map<String, Object> map) throws Exception;
	/**
	 * 重置密码
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ResponseBean updatePassword(Map<String, Object> map) throws Exception;
	
}
