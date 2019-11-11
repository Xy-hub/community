package com.myspringboot.community.dto;

import lombok.Data;

/**
 * 创建回复的数据传输对象
 */
@Data
public class CommentCreateDTO {

    private Integer parentId;
    private String content;
    private Integer type;

}
