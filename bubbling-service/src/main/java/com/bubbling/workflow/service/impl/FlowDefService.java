package com.bubbling.workflow.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.utils.BaseUtils;
import com.bubbling.frame.base.utils.IoUtils;
import com.bubbling.workflow.dao.TActDeploymentMapper;
import com.bubbling.workflow.entity.TActDeployment;
import com.bubbling.workflow.service.IFlowDefService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

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
    private TActDeploymentMapper tActDeploymentMapper;
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
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/test\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1621667210359\" name=\"\" targetNamespace=\"http://www.activiti.org/test\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <process id=\"holiday\" name=\"请假\" isExecutable=\"true\">\n" +
                "    <startEvent id=\"start\" name=\"开始\">\n" +
                "      <outgoing>SequenceFlow_0h21x7r</outgoing>\n" +
                "    </startEvent>\n" +
                "    <sequenceFlow id=\"SequenceFlow_0h21x7r\" sourceRef=\"start\" targetRef=\"sc\" />\n" +
                "    <userTask id=\"sc\" name=\"审查\" activiti:assignee=\"${scUser}\" activiti:candidateGroups=\"${org}\">\n" +
                "      <incoming>SequenceFlow_0h21x7r</incoming>\n" +
                "      <outgoing>Flow_1xex6al</outgoing>\n" +
                "    </userTask>\n" +
                "    <sequenceFlow id=\"Flow_1xex6al\" sourceRef=\"sc\" targetRef=\"sh\" />\n" +
                "    <userTask id=\"sh\" name=\"审核\" activiti:assignee=\"${shUser}\" activiti:candidateGroups=\"${org}\">\n" +
                "      <incoming>Flow_1xex6al</incoming>\n" +
                "      <outgoing>Flow_000uhqe</outgoing>\n" +
                "    </userTask>\n" +
                "    <endEvent id=\"end\" name=\"结束\">\n" +
                "      <incoming>Flow_000uhqe</incoming>\n" +
                "    </endEvent>\n" +
                "    <sequenceFlow id=\"Flow_000uhqe\" sourceRef=\"sh\" targetRef=\"end\" />\n" +
                "  </process>\n" +
                "  <bpmndi:BPMNDiagram id=\"BpmnDiagram_1\">\n" +
                "    <bpmndi:BPMNPlane id=\"BpmnPlane_1\" bpmnElement=\"holiday\">\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_000uhqe_di\" bpmnElement=\"Flow_000uhqe\">\n" +
                "        <omgdi:waypoint x=\"560\" y=\"120\" />\n" +
                "        <omgdi:waypoint x=\"682\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_1xex6al_di\" bpmnElement=\"Flow_1xex6al\">\n" +
                "        <omgdi:waypoint x=\"340\" y=\"120\" />\n" +
                "        <omgdi:waypoint x=\"460\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"SequenceFlow_0h21x7r_di\" bpmnElement=\"SequenceFlow_0h21x7r\">\n" +
                "        <omgdi:waypoint x=\"188\" y=\"120\" />\n" +
                "        <omgdi:waypoint x=\"240\" y=\"120\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNShape id=\"StartEvent_1y45yut_di\" bpmnElement=\"start\">\n" +
                "        <omgdc:Bounds x=\"152\" y=\"102\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"160\" y=\"145\" width=\"23\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1o4tw34_di\" bpmnElement=\"sc\">\n" +
                "        <omgdc:Bounds x=\"240\" y=\"80\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_01bt4tv_di\" bpmnElement=\"sh\">\n" +
                "        <omgdc:Bounds x=\"460\" y=\"80\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Event_0jgcqxp_di\" bpmnElement=\"end\">\n" +
                "        <omgdc:Bounds x=\"682\" y=\"102\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"689\" y=\"145\" width=\"23\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "    </bpmndi:BPMNPlane>\n" +
                "  </bpmndi:BPMNDiagram>\n" +
                "</definitions>\n";
        //使用RepositoryService进行部署
        Deployment dev = repositoryService.createDeployment()
                .category(category)//类别
                .key(key)//唯一编码
                .name(name)//名称
                .addString("bpmn", bpmn)//xml
                .deploy();
        TActDeployment tActDeployment=new TActDeployment();
        tActDeployment.setDevKey(key);
        tActDeployment.setCategory(category);
        tActDeployment.setDevName(name);
        tActDeployment.setDevId(dev.getId());
        tActDeploymentMapper.insert(tActDeployment);
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
        String category=(String) map.get("category");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("dev_key",key);
        TActDeployment tActDeployment = tActDeploymentMapper.selectOne(queryWrapper);
        Deployment dev = repositoryService.createDeployment()
                .category(category)//类别
                .key(key)//唯一编码
                .name(tActDeployment.getDevName())//名称
                .addString("bpmn", bpmn)//xml
                .deploy();

        tActDeployment.setDevId(dev.getId());
        tActDeploymentMapper.updateById(tActDeployment);
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
        QueryWrapper queryWrapper=new QueryWrapper();
        if(BaseUtils.isNotNull(category)){
            queryWrapper.like("category",category);
        }
        if(BaseUtils.isNotNull(key)){
            queryWrapper.like("dev_key",key);
        }
        if(BaseUtils.isNotNull(name)){
            queryWrapper.like("dev_name",name);
        }
        List<TActDeployment> list = tActDeploymentMapper.selectList(queryWrapper);
        List<Map<String,Object>> rtnMap=new ArrayList<Map<String,Object>>();
        for (TActDeployment tActDeployment : list) {
            Map<String,Object> res = new HashMap<>();
            res.put("id",tActDeployment.getId());
            res.put("category",tActDeployment.getCategory());
            res.put("key",tActDeployment.getDevKey());
            res.put("name",tActDeployment.getDevName());
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
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("dev_key",key);
        TActDeployment tActDeployment = tActDeploymentMapper.selectOne(queryWrapper);
        InputStream bpmnInput = repositoryService.getResourceAsStream(tActDeployment.getDevId(), "bpmn");
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
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("dev_key",key);
        tActDeploymentMapper.delete(queryWrapper);
        return ResponseBean.success();
    }
    /**
     *查询全部流程定义并转换成树
     *@param map
     *@Return:com.bubbling.frame.base.bean.ResponseBean
     *@Author:dc_yangwen
     *@Date:2021-05-17 21:07
     */
    @Override
    public ResponseBean queryAllFlowDef(Map<String, Object> map) throws Exception {
        QueryWrapper queryWrapper=new QueryWrapper();
        List<TActDeployment> list = tActDeploymentMapper.selectList(queryWrapper);
        TreeMap<String,String> treeMap=new TreeMap<>();
        for (TActDeployment deployment : list) {
            treeMap.put(deployment.getCategory(),"");
        }
        List<Map<String,Object>> rtnMap=new ArrayList<Map<String,Object>>();
        Map<String,Object> res;
        for (Map.Entry<String, String> stringStringEntry : treeMap.entrySet()) {
            res = new HashMap<>();
            res.put("id",stringStringEntry.getKey());
            res.put("category","1");
            res.put("name",stringStringEntry.getKey());
            rtnMap.add(res);
        }
        for (TActDeployment deployment : list) {
            res = new HashMap<>();
            res.put("id",deployment.getId());
            res.put("category",deployment.getCategory());
            res.put("key",deployment.getDevKey());
            res.put("name",deployment.getDevName());
            rtnMap.add(res);
        }
        return ResponseBean.success(rtnMap);
    }

}
