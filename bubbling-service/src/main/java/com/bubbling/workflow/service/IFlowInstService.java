package com.bubbling.workflow.service;

import com.bubbling.frame.base.bean.ResponseBean;

import java.util.Map;

/**
 * @title: IFlowInstService
 * @Author yangwen
 * @Date: 2021-05-17 21:06
 * @ignore
 */
public interface IFlowInstService {
    ResponseBean queryFlowInstByKey(Map<String, Object> map) throws Exception;

    ResponseBean startFlowInst(Map<String, Object> map) throws Exception;

    ResponseBean queryActiveInstByKey(Map<String, Object> map) throws Exception;

    ResponseBean commitActive(Map<String, Object> map) throws Exception;
}
