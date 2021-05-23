package com.bubbling.workflow.service.impl;

import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.utils.BaseUtils;
import com.bubbling.workflow.service.IFlowInstService;
import com.bubbling.workflow.service.IWorkFlowService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: 流程实例测试
 * @Author yangwen
 * @Date: 2021-05-17 21:06
 * @ignore
 */
@Service
public class FlowInstService implements IFlowInstService {
    @Autowired
    private IWorkFlowService workFlowService;
    /**
     *通过key查询流程实例
     *@param map
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-17 21:47
     */
    @Override
    public ResponseBean queryFlowInstByKey(Map<String, Object> map) throws Exception {
        String key=(String) map.get("key");
        List<ProcessInstance> processInstances = workFlowService.queryFlowInstByKey(key);
        if (BaseUtils.isNullList(processInstances)) {
            return ResponseBean.error("未查询到流程实例信息");
        }
        List<Map<String,String>> rtnMap=new ArrayList<>();
        Map<String,String> addMap;
        for (ProcessInstance processInstance : processInstances) {
            addMap=new HashMap<>();
            addMap.put("id",processInstance.getProcessInstanceId());
            addMap.put("key",processInstance.getProcessDefinitionKey());
            addMap.put("name",processInstance.getProcessDefinitionName());
            addMap.put("businessKey",processInstance.getBusinessKey());
            rtnMap.add(addMap);
        }
        return ResponseBean.success(rtnMap);
    }
    /**
     *启动流程
     *@param map businessId
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-17 21:53
     */
    @Override
    public ResponseBean startFlowInst(Map<String, Object> map) throws Exception {
        String key=(String) map.get("key");
        String businessKey=(String) map.get("businessKey");
        Map<String,Object> assigneeMap= (Map<String, Object>) map.get("assigneeMap");
        if (workFlowService.startFlowInst(key,businessKey,assigneeMap)) {
            return ResponseBean.success();
        }
        return ResponseBean.error("流程启动失败");
    }
    /**
     *查询环节实例信息
     *@param map flowInstId:流程实例id
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-22 10:27
     */
    @Override
    public ResponseBean queryActiveInstByKey(Map<String, Object> map) throws Exception {
        String flowInstId=(String) map.get("flowInstId");
        List<Map<String, Object>> historicTaskInstances = workFlowService.queryAllActiveInst(flowInstId);
        return ResponseBean.success(historicTaskInstances);
    }
    /**
     *提交流程
     *@param map activeId:环节id assigneeMap:参数
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-23 20:10
     */
    @Override
    public ResponseBean commitActive(Map<String, Object> map) throws Exception {
        String activeId=(String) map.get("activeId");
        Map<String,Object> assigneeMap= (Map<String, Object>) map.get("assigneeMap");
        workFlowService.commitActive(activeId,assigneeMap);
        return ResponseBean.success();
    }


}
