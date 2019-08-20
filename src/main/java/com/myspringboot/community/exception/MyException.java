package com.myspringboot.community.exception;

/**
 * 自定义异常类
 */
public class MyException extends RuntimeException{
    private String message;
    private Integer code;

    public MyException(IMyErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
