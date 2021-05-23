package com.bubbling.workflow.service;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface IWorkFlowService {
    /**
     *启动流程
     *@param key 定义编码
     *@param businessKey 业务id
     *@Return:boolean
     *@Author:dc_yangwen
     *@Date:2021-05-17 22:04
     */
    boolean startFlowInst(String key, String businessKey, Map<String,Object> assigneeMap) throws Exception;
    /**
     *根据key获取流程实例信息
     *@param key 流程编码
     *@Return:java.util.List<org.activiti.engine.runtime.ProcessInstance>
     *@Author:dc_yangwen
     *@Date:2021-05-20 20:03
     */
    List<ProcessInstance> queryFlowInstByKey(String key);
    /**
     *查询全部环节实例，包括历史信息
     *@param flwoInstId 流程实例id
     *@Return:java.util.List<org.activiti.engine.task.Task>
     *@Author:dc_yangwen
     *@Date:2021-05-22 10:38
     */
    List<Map<String, Object>> queryAllActiveInst(String flwoInstId) throws Exception;
    /**
     *提交流程
     *@param activeId
     *@param assigneeMap
     *@Return:boolean
     *@Author:dc_yangwen
     *@Date:2021-05-23 20:13
     */
    boolean commitActive(String activeId, Map<String, Object> assigneeMap) throws Exception;
}
