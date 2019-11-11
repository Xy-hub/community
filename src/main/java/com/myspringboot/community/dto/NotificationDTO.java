package com.myspringboot.community.dto;

import com.myspringboot.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    //通知id
    private Integer id;
    private Long gmtCreate;
    //已读还是未读
    private Integer status;
    private String notifierName;
    private Integer notifier;
    //通知的是哪一个问题或者回复
    private String outerTitle;
    private Integer outerId;
    //通知是问题还是回复
    private Integer type;
    private String typeName;
}
