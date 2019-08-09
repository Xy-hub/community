package com.myspringboot.community.dto;

import com.myspringboot.community.model.User;
import lombok.Data;

/**
 * Question中没有User属性，使用DTO数据传输对象加入User 可以代替Question进行数据绑定，这里时为了获取每一个user的头像
 * 但也可以直接用mybatis级联操作解决该问题
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
