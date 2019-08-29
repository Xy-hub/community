package com.myspringboot.community.dto;

import com.myspringboot.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private Integer notifier;
    private String outerTitle;
    private Integer outerId;
    private Integer type;
    private String typeName;
}
