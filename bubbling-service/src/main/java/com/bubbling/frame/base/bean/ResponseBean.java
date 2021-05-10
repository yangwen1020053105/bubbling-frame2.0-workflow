package com.bubbling.frame.base.bean;

import com.bubbling.frame.base.constants.SrvConstants;
import org.springframework.stereotype.Component;

@Component
public class ResponseBean<T> {
    private int rtnCode;
    private String rtnMsg;
    private T data;

    public int getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(int rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseBean rtn(int rtnCode, String rtnMsg, Object data) {
        ResponseBean response = new ResponseBean();
        response.setRtnCode(rtnCode);
        response.setRtnMsg(rtnMsg);
        response.setData(data);
        return response;
    }

    public static ResponseBean success() {
        ResponseBean response = new ResponseBean();
        response.setRtnCode(SrvConstants.RTN_CODE_SUCCESS);
        response.setRtnMsg("success");
        return response;
    }

    public static ResponseBean success(Object data) {
        ResponseBean response = new ResponseBean();
        response.setRtnCode(SrvConstants.RTN_CODE_SUCCESS);
        response.setRtnMsg("success");
        response.setData(data);
        return response;
    }

    public static ResponseBean error(int rtnCode, String rtnMsg) {
        ResponseBean response = new ResponseBean();
        response.setRtnCode(rtnCode);
        response.setRtnMsg(rtnMsg);
        return response;
    }
    public static ResponseBean error( String rtnMsg) {
        ResponseBean response = new ResponseBean();
        response.setRtnCode(SrvConstants.RTN_CODE_FAIL);
        response.setRtnMsg(rtnMsg);
        return response;
    }
}
