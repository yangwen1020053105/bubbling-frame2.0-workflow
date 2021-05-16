package com.bubbling.workflow.controller;

import com.bubbling.workflow.utils.SecurityUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: TestController
 * @Author yangwen
 * @Date: 2021/4/28 10:04
 * @ignore
 */
@RestController
public class TestController {
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    /**
     *启动流程 （act_ru_task会插入实例数据）
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021/5/6 15:56
     */
    @GetMapping("/startFlow")
    public String startFlow(){
        String flowCode="myProcess";//流程定义code
        String businessKey="1000";//业务数据id
        Map<String,Object> assigneeMap = new HashMap<>();
        assigneeMap.put("org","activitiTeam");//设置操作部门为activitiTeam
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(flowCode,businessKey,assigneeMap);//启动流程
        System.out.println("启动成功，流程实例id："+processInstance.getId()+"业务id："+processInstance.getBusinessKey());
        return "启动成功，流程实例id："+processInstance.getId()+"业务id："+processInstance.getBusinessKey();
    }
    /**
     *查询code为myProcess的所有流程实例信息
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021/5/6 16:37
     */
    @GetMapping("/getFlowInfo")
    public String getFlowInfo(){
        // 流程定义key
        String flowCode = "myProcess";
        List<ProcessInstance> list = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(flowCode)
                .list();
        for (ProcessInstance processInstance : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + processInstance.getProcessInstanceId());
            System.out.println("所属流程定义id：" + processInstance.getProcessDefinitionId());
            System.out.println("是否执行完成：" + processInstance.isEnded());
            System.out.println("是否暂停：" + processInstance.isSuspended());
            System.out.println("当前活动标识：" + processInstance.getActivityId());
        }
        return "请查看日志";
    }
    /**
     *查询当前组下所有环节实例
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021/5/6 17:01
     */
    @GetMapping("/getActiveInfo")
    public String getActiveInfo(){
        String flowCode = "myProcess";
        //查询组任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(flowCode)
                .taskCandidateUser("rose")//查询rose所在组的所有流程信息
                .taskDefinitionKey("shenpi1")//查询审批1环节
                .list();
        for (Task task : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("环节实例id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
        return "请查看日志";
    }
    /**
     *指派任务
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021/5/6 17:05
     */
    @GetMapping("/assignTask")
    public String assignTask(){
        String taskId = "0a358b5e-ae50-11eb-91b9-8cec4b5fe9f1";//环节实例id
        String userId = "rose";//被指派人
        //查询任务
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskCandidateUser(userId)//根据候选人查询
                .singleResult();
        if(task!=null){
            taskService.claim(taskId, userId);//指派任务
            System.out.println("任务指派成功");
        }
        return "请查看日志";
    }
    /**
     *查询个人待办任务
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021/5/6 17:09
     */
    @GetMapping("/getMyTask")
    public String getMyTask(){
        // 流程定义kcode
        String flowCode = "myProcess";
        // 环节独占人
        String assignee = "rose";
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(flowCode)
                .taskAssignee(assignee)//查询rose名下的任务
                .taskDefinitionKey("shenpi1")//查询审批1环节
                .list();
        for (Task task : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("环节实例id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
        return "请查看日志";
    }
    /**
     *提交结论
     *@Return:java.lang.String
     *@Author:dc_yangwen
     *@Date:2021/5/6 17:09
     */
    @GetMapping("/commitMyTask")
    public String commitMyTask(){
        securityUtil.logInAs("rose");
        //     任务ID
        String taskId = "0a358b5e-ae50-11eb-91b9-8cec4b5fe9f1";
        Map<String, Object> map=new HashMap<>();
//        map.put("test","rose");//设置下一环节的独占人
        taskService.complete(taskId,map);
        System.out.println("完成任务："+taskId);
        return "请查看日志";
    }
}

