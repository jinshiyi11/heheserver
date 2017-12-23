package com.shuai.hehe.server.exception;


import com.shuai.hehe.server.response.ErrorCode;

/**
 *
 */
public class ServiceException extends RuntimeException {
    private int mErrorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        mErrorCode = errorCode.getErrorCode();
    }

    public ServiceException(int errorCode, String message) {
        super(message);
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setmErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }
}
