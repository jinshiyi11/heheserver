package com.shuai.hehe.server.exception;


import com.shuai.hehe.server.response.ErrorCode;
import com.shuai.hehe.server.response.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * 统一处理异常
 */
@ControllerAdvice
public class ServiceExceptionHandler {
    private final static Logger sLogger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseInfo handle(Exception exception) {
        if(exception instanceof ServiceException){
            ServiceException serviceException= (ServiceException) exception;
            return new ResponseInfo(serviceException.getErrorCode(), serviceException.getMessage());
        }else if(exception instanceof ConstraintViolationException){
            //TODO:message
            ConstraintViolationException constraintViolationException= (ConstraintViolationException) exception;
            return new ResponseInfo(ErrorCode.ERROR_INVALID_PARAM.getErrorCode(), constraintViolationException.getMessage());
        }else{
            sLogger.error("未知异常",exception);
            return new ResponseInfo(ErrorCode.ERROR_UNKNOWN.getErrorCode(),exception.getMessage());
        }
    }
}
