package com.ozf.laiyw.manage.common.commons;

import java.io.Serializable;

/**
 * @Auther: Laiyw
 * @Date: 2018/7/19 15:19
 * @Description:
 */
public class WebResult<T> implements Serializable {

    private boolean success;
    private int code;
    private String message;
    private T data;

    public static WebResult successResult() {
        return new WebResult(true, 200, Constants.SUCCESS_MESSAGE);
    }

    /**
     * @param message
     * @return
     */
    public static WebResult successResult(String message) {
        return new WebResult(true, 200, message);
    }

    /**
     * @param data
     * @return
     */
    public static <T> WebResult successResult(T data) {
        return new WebResult(true, 200, Constants.SUCCESS_MESSAGE, data);
    }

    /**
     * @param message
     * @param data
     * @return
     */
    public static <T> WebResult successResult(String message, T data) {
        return new WebResult(true, 200, message, data);
    }

    /**
     * 网络异常
     *
     * @return
     */
    public static WebResult errorNetworkAnomalyResult() {
        return new WebResult(false, 500, Constants.ERROR_MESSAGE_NETWORK_ANOMALY);
    }

    /**
     * 请求参数不足
     *
     * @return
     */
    public static WebResult errorLackOfParameterResult() {
        return new WebResult(false, 400, Constants.ERROR_MESSAGE_LACK_OF_PARAMETER);
    }

    /**
     * @param message
     * @return
     */
    public static WebResult errorResult(String message) {
        return new WebResult(false, 500, message);
    }

    public WebResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public WebResult(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
