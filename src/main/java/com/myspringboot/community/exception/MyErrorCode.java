package com.myspringboot.community.exception;

/**
 * 枚举类，自定义异常状态码和信息
 */
public enum MyErrorCode implements IMyErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的问题不存在!"),
    TARGET_PARAM_NOT_FOUOND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登陆后重试！"),
    SYS_ERROR(2004,"服务器冒烟，页面消失了，再试试吧~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006,"你找的评论不存在了！"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008,"这是别人的信息！");
    private Integer code;
    private String message;

    MyErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
