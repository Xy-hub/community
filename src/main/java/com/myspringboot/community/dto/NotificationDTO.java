package com.myspringboot.community.dto;

import com.myspringboot.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private User user;
    private String title;
    private String type;
}
