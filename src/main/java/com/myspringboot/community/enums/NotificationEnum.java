package com.myspringboot.community.enums;

/**
 * 通知枚举
 */
public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int status, String name){
        this.status=status;
        this.name=name;
    }

    //根据type返回通知的内容
    public static String nameOfStatus(int type){
        for (NotificationEnum value : NotificationEnum.values()) {
            if(value.getStatus()==type){
                return value.getName();
            }
        }
        return "";
    }
}
