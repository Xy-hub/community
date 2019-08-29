package com.myspringboot.community.controller;

import com.myspringboot.community.dto.CommentCreateDTO;
import com.myspringboot.community.dto.CommentDTO;
import com.myspringboot.community.dto.ResultDTO;
import com.myspringboot.community.enums.CommentTypeEnum;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.mapper.CommentMapper;
import com.myspringboot.community.model.Comment;
import com.myspringboot.community.model.User;
import com.myspringboot.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    /**
     * 回复功能
     * @param commentDTO  页面传入的comment参数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentDTO, HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        //判断是否登录
        if(user==null){
            return ResultDTO.errorOf(MyErrorCode.NO_LOGIN);
        }
        //判断评论的内容是否为空
        if(commentDTO==null|| StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(MyErrorCode.CONTENT_IS_EMPTY);
        }
        //回复变成一个对象插入数据库
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setCommentator(Integer.valueOf(user.getAccountId()));
        commentService.insert(comment,user);
        return ResultDTO.successOf();
    }

    /**
     * 展开二级评论
     * @param id 父评论id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.successOf(commentDTOS);
    }
}
