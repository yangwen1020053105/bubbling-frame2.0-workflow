package com.bubbling.workflow.service.impl;

import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.utils.BaseUtils;
import com.bubbling.frame.base.utils.DateUtils;
import com.bubbling.workflow.service.IWorkFlowService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.ProcessInstanceQueryProperty;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @title: WorkFlowService
 * @Author yangwen
 * @Date: 2021-05-17 21:55
 * @ignore
 */
@Service
public class WorkFlowService implements IWorkFlowService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    /**
     *启动流程
     *@param key 定义编码
     *@param businessKey 业务id
     *@Return:boolean
     *@Author:dc_yangwen
     *@Date:2021-05-17 22:04
     */
    @Override
    public boolean startFlowInst(String key, String businessKey,Map<String,Object> assigneeMap) throws Exception {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key,businessKey, assigneeMap);
        if (BaseUtils.isNotNull(processInstance)) {
            return true;
        }
        return false;
    }
    /**
     *根据key获取流程实例信息
     *@param key 流程编码
     *@Return:java.util.List<org.activiti.engine.runtime.ProcessInstance>
     *@Author:dc_yangwen
     *@Date:2021-05-20 20:03
     */
    @Override
    public List<ProcessInstance> queryFlowInstByKey(String key){
        QueryProperty queryProperty=new ProcessInstanceQueryProperty("start_time_");
        return runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(key)
                .orderBy(queryProperty)
                .desc()
                .list();
    }
    /**
     *查询全部环节实例，包括历史信息
     *@param flwoInstId 流程实例id
     *@Return:java.util.List<org.activiti.engine.task.Task>
     *@Author:dc_yangwen
     *@Date:2021-05-22 10:38
     */
    @Override
    public List<Map<String, Object>> queryAllActiveInst(String flwoInstId) throws Exception {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> his = historicTaskInstanceQuery.processInstanceId(flwoInstId).list();
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(flwoInstId)
                .list();
        List<String> activeArr=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            activeArr.add(list.get(0).getId());
        }
        List<Map<String, Object>> rtnMap=new ArrayList<>();
        Map<String, Object> stringObjectMap;
        for (HistoricTaskInstance hi : his) {
            stringObjectMap = BaseUtils.objectToMap(hi);
            long startTime = (long) stringObjectMap.get("startTime");
            if(BaseUtils.isNotNull(startTime)){
                stringObjectMap.put("startTime", DateUtils.dateToyyyyMMddhhssmm(new Date(startTime)));
            }
            if(activeArr.contains(hi.getId())){
                stringObjectMap.put("activeStatus","active");
            }else{
                stringObjectMap.put("activeStatus","over");
            }
            rtnMap.add(stringObjectMap);
        }
        return rtnMap;
    }
    /**
     *提交流程
     *@param activeId
     *@param assigneeMap
     *@Return:boolean
     *@Author:dc_yangwen
     *@Date:2021-05-23 20:13
     */
    @Override
    public boolean commitActive(String activeId,Map<String,Object> assigneeMap) throws Exception {
        taskService.complete(activeId,assigneeMap);
        return true;
    }
}
