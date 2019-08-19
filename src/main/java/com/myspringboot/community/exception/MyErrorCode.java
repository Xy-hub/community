package com.myspringboot.community.exception;

public enum MyErrorCode implements IMyErrorCode{

    QUESTION_NOT_FOUND("你找的问题不存在!");
    private String message;

    MyErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
