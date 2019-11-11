package com.myspringboot.community.dto;

import com.myspringboot.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    //评论的是上一级的id
    private Integer parentId;
    //问题还是回复
    private Integer type;
    //作者
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
    private User user;

 }