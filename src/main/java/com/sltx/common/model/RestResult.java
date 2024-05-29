package com.sltx.common.model;

import java.io.Serializable;

import com.sltx.common.Consts;


public class RestResult<T> implements Serializable {

    private String code;
    private String msg;

    private T data;

    public RestResult() {

    }

    public RestResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RestResult buildSuccess() {
        RestResult restResult = new RestResult();
        restResult.success();
        return restResult;
    }

    public static RestResult buildSuccess(Object t) {
        RestResult restResult = buildSuccess();
        restResult.setData(t);
        return restResult;
    }

    public static RestResult buildError() {
        RestResult restResult = new RestResult();
        restResult.error();
        return restResult;
    }

    public static RestResult buildError(String msg) {
        RestResult restResult = new RestResult();
        restResult.error(msg);
        return restResult;
    }

    public RestResult success() {
        this.code = Consts.SUCCESS;
        this.msg = "Successful operation";
        return this;
    }

    public RestResult success(T t) {
        success();
        this.data = t;
        return this;
    }

    public RestResult error() {
        this.code = Consts.ERROR;
        this.msg = "operation failed";
        return this;
    }

    public RestResult error(String msg) {
        this.code = Consts.ERROR;
        this.msg = msg;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
