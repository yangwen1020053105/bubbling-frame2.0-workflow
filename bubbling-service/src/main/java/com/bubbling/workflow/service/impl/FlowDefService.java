package com.bubbling.workflow.service.impl;


import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.utils.BaseUtils;
import com.bubbling.frame.base.utils.IoUtils;
import com.bubbling.workflow.dao.TActBytearrayVersionMapper;
import com.bubbling.workflow.entity.TActBytearrayVersion;
import com.bubbling.workflow.service.IFlowDefService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

/**
 * @title: 流程图设计
 * @Author yangwen
 * @Date: 2021-05-15 22:15
 * @ignore
 */
@Service
public class FlowDefService implements IFlowDefService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TActBytearrayVersionMapper tActBytearrayVersionMapper;
    /**
     *部署流程
     *@param map xml:流程信息
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-15 22:20
     */
    @Override
    public ResponseBean deploymentProcess(Map<String, Object> map) throws Exception {
        System.out.println(map.toString());
        String category=(String) map.get("category");
        String key=(String) map.get("key");
        String name=(String) map.get("name");
        String bpmn="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\"sid-38422fae-e03e-43a3-bef4-bd33b32041b2\" targetNamespace=\"http://bpmn.io/bpmn\" exporter=\"bpmn-js (https://demo.bpmn.io)\" exporterVersion=\"5.1.2\">\n" +
                "  <process id=\""+key+"\" name=\""+name+"\" isExecutable=\"true\">\n" +
                "    <startEvent id=\"StartEvent_1y45yut\" name=\"开始\">\n" +
                "      <outgoing>SequenceFlow_0h21x7r</outgoing>\n" +
                "    </startEvent>\n" +
                "    <task id=\"test\" name=\"测试\">\n" +
                "      <incoming>SequenceFlow_0h21x7r</incoming>\n" +
                "      <outgoing>Flow_0mnt4ho</outgoing>\n" +
                "    </task>\n" +
                "    <sequenceFlow id=\"SequenceFlow_0h21x7r\" sourceRef=\"StartEvent_1y45yut\" targetRef=\"test\" />\n" +
                "    <intermediateThrowEvent id=\"Event_11k45g9\">\n" +
                "      <incoming>Flow_0mnt4ho</incoming>\n" +
                "    </intermediateThrowEvent>\n" +
                "    <sequenceFlow id=\"Flow_0mnt4ho\" sourceRef=\"test\" targetRef=\"Event_11k45g9\" />\n" +
                "  </process>\n" +
                "  <bpmndi:BPMNDiagram id=\"BpmnDiagram_1\">\n" +
                "    <bpmndi:BPMNPlane id=\"BpmnPlane_1\" bpmnElement=\"testFlow\">\n" +
                "      <bpmndi:BPMNEdge id=\"SequenceFlow_0h21x7r_di\" bpmnElement=\"SequenceFlow_0h21x7r\">\n" +
                "        <omgdi:waypoint x=\"188\" y=\"120\" />\n" +
                "        <omgdi:waypoint x=\"240\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0mnt4ho_di\" bpmnElement=\"Flow_0mnt4ho\">\n" +
                "        <omgdi:waypoint x=\"340\" y=\"120\" />\n" +
                "        <omgdi:waypoint x=\"392\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNShape id=\"StartEvent_1y45yut_di\" bpmnElement=\"StartEvent_1y45yut\">\n" +
                "        <omgdc:Bounds x=\"152\" y=\"102\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"160\" y=\"145\" width=\"22\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Task_1hcentk_di\" bpmnElement=\"test\">\n" +
                "        <omgdc:Bounds x=\"240\" y=\"80\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Event_11k45g9_di\" bpmnElement=\"Event_11k45g9\">\n" +
                "        <omgdc:Bounds x=\"392\" y=\"102\" width=\"36\" height=\"36\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "    </bpmndi:BPMNPlane>\n" +
                "  </bpmndi:BPMNDiagram>\n" +
                "</definitions>\n";
        //使用RepositoryService进行部署
        repositoryService.createDeployment()
                .category(category)//类别
                .key(key)//唯一编码
                .name(name)//名称
                .addString("bpmn", bpmn)//xml
                .deploy();
        TActBytearrayVersion tActBytearrayVersion=new TActBytearrayVersion();
        tActBytearrayVersion.setFlowKey(key);
        tActBytearrayVersion.setName(name);
        tActBytearrayVersion.setCategory(category);
        tActBytearrayVersion.setBytes(bpmn.getBytes());
        tActBytearrayVersionMapper.insert(tActBytearrayVersion);
        return ResponseBean.success();
    }
    /**
     *重新部署
     *@param map bpmn:bpmn
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 17:24
     */
    @Override
    public ResponseBean updateProcess(Map<String, Object> map) throws Exception {
        String bpmn=(String) map.get("bpmn");
        String key=(String) map.get("key");
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        deploymentQuery.deploymentKey(key);
        Deployment deployment = deploymentQuery.singleResult();
        String category=deployment.getCategory();
        String name=deployment.getName();
        repositoryService.deleteDeployment(deployment.getId());
        repositoryService.createDeployment()
                .category(category)//类别
                .key(key)//唯一编码
                .name(name)//名称
                .tenantId(key)
                .addString("bpmn", bpmn)//xml
                .deploy();
        TActBytearrayVersion tActBytearrayVersion=new TActBytearrayVersion();
        tActBytearrayVersion.setFlowKey(key);
        tActBytearrayVersion.setName(name);
        tActBytearrayVersion.setCategory(category);
        tActBytearrayVersion.setBytes(bpmn.getBytes());
        tActBytearrayVersionMapper.insert(tActBytearrayVersion);
        return ResponseBean.success();
    }
    /**
     *查询流程定义信息
     *@param map category:类别,key:唯一编码,name:名称,
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 15:32
     */
    @Override
    public ResponseBean queryFlowDef(Map<String, Object> map) throws Exception {
        String category=(String) map.get("category");
        String key=(String) map.get("key");
        String name=(String) map.get("name");
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        if(BaseUtils.isNotNull(category)){
            deploymentQuery.deploymentCategoryLike(category);
        }
        if(BaseUtils.isNotNull(key)){
            deploymentQuery.deploymentKeyLike(key);
        }
        if(BaseUtils.isNotNull(name)){
            deploymentQuery.deploymentNameLike(name);
        }
        List<Deployment> list = deploymentQuery.list();
        list.sort((Deployment o1, Deployment o2)->{
            return o1.getCategory().compareTo(o2.getCategory());
        });
        List<Map<String,Object>> rtnMap=new ArrayList<Map<String,Object>>();
        for (Deployment deployment : list) {
            Map<String,Object> res = new HashMap<>();
            res.put("id",deployment.getId());
            res.put("category",deployment.getCategory());
            res.put("key",deployment.getKey());
            res.put("name",deployment.getName());
            rtnMap.add(res);
        }
        return ResponseBean.success(rtnMap);
    }
    /**
     *获取流程xml
     *@param map id:流程定义id
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 16:18
     */
    @Override
    public ResponseBean queryFlowDefXml(Map<String, Object> map) throws Exception {
        String key=(String) map.get("key");
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey(key).singleResult();
        InputStream bpmnInput = repositoryService.getResourceAsStream(deployment.getId(), "bpmn");
        String bpmn = IoUtils.inputStreamToString(bpmnInput);
        return ResponseBean.success(bpmn);
    }
    /**
     *删除流程定义
     *@param map
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-16 17:12
     */
    @Override
    public ResponseBean delFlowDef(Map<String, Object> map) throws Exception {
        String key=(String) map.get("key");
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> list = deploymentQuery.deploymentKey(key).list();
        for (Deployment deployment : list) {
            repositoryService.deleteDeployment(deployment.getId());
        }
        return ResponseBean.success();
    }

}
