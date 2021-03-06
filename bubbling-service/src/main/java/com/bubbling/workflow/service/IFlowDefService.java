package com.bubbling.workflow.service;

import com.bubbling.frame.base.bean.ResponseBean;

import java.util.Map;

/**
 * @title: 流程图设计
 * @Author yangwen
 * @Date: 2021-05-15 22:15
 * @ignore
 */
public interface IFlowDefService {
    /**
     *部署流程
     *@param map xml:流程信息
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-15 22:20
     */
    ResponseBean deploymentProcess(Map<String, Object> map) throws Exception;

    ResponseBean updateProcess(Map<String, Object> map) throws Exception;

    /**
     *查询流程定义信息
     *@param map category:类别,key:唯一编码,name:名称,
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 15:32
     */
    ResponseBean queryFlowDef(Map<String, Object> map) throws Exception;
    /**
     *获取流程xml
     *@param map id:流程定义id
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 16:18
     */
    ResponseBean queryFlowDefXml(Map<String, Object> map) throws Exception;
    /**
     *删除流程定义
     *@param map
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 17:12
     */
    ResponseBean delFlowDef(Map<String, Object> map) throws Exception;
    /**
     *查询全部流程定义并转换成树
     *@param map
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-17 21:07
     */
    ResponseBean queryAllFlowDef(Map<String, Object> map) throws Exception;
}
