package com.sltx.common.model;

import java.io.Serializable;

/**
 * rpc return result
 * @author Rlax
 *
 */
public class RetResult<T> implements Serializable {

    /** success  failure*/
    private boolean success = true;
    /** returned messages */
    private T data;

    /** error message */
    private ErrorResult error;

    public static <T> RetResult buildSuccess() {
        return buildSuccess(null);
    }

    public static <T> RetResult buildSuccess(T t) {
        RetResult<T> rpcResult = new RetResult<T>();
        rpcResult.success(t);
        return rpcResult;
    }

    public static <T> RetResult buildError() {
        return buildError(ErrorResult.NORMAL_ERROR, "operation failed");
    }

    public static <T> RetResult buildValidatorError(String msg) {
        return buildError(ErrorResult.VALIDATOR_ERROR, msg);
    }

    public static <T> RetResult buildBusinessError(String msg) {
        return buildError(ErrorResult.BUSINESS_ERROR, msg);
    }

    public static <T> RetResult buildSystemError(String msg) {
        return buildError(ErrorResult.SYSTEM_ERROR, msg);
    }

    public static <T> RetResult buildError(Integer code, String msg) {
        RetResult<T> rpcResult = new RetResult<T>();
        rpcResult.error(code, msg);
        return rpcResult;
    }

    private void success(T t) {
        this.success = true;
        this.data = t;
        this.error = null;
    }

    private void error(Integer code, String msg) {
        this.success = false;
        this.data = null;
        this.error = new ErrorResult(code, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResult getError() {
        return error;
    }

    public void setError(ErrorResult error) {
        this.error = error;
    }
}
