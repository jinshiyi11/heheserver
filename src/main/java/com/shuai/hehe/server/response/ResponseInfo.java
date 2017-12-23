package com.shuai.hehe.server.response;


/**
 *
 */
public class ResponseInfo<T> {
    private int code = ErrorCode.ERROR_SUCCESS.getErrorCode();

    //@JsonProperty("msg")
    private String msg;

    private T data;

    public ResponseInfo() {
    }

    public ResponseInfo(int errorCode, String message) {
        code = errorCode;
        msg = message;
    }

    public ResponseInfo(ErrorCode error) {
        code = error.getErrorCode();
        msg = error.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int errorCode) {
        this.code = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
