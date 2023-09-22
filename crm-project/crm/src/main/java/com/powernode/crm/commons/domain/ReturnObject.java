package com.powernode.crm.commons.domain;

/**
 * @title:ReturnObject Author liu
 * @Date:2023/3/29 14:49
 * @Version 1.0
 */
public class ReturnObject {
    private String code;//处理成功或失败的标记==1成功==0失败
    private String message;//提示信息
    private Object retData;//其他数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
