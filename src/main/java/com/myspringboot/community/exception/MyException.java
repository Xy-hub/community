package com.myspringboot.community.exception;

/**
 * 自定义异常类
 */
public class MyException extends RuntimeException{
    private String message;

    public MyException(IMyErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public MyException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
