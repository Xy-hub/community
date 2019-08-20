package com.myspringboot.community.controller;

import com.myspringboot.community.dto.CommentDTO;
import com.myspringboot.community.dto.ResultDTO;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.mapper.CommentMapper;
import com.myspringboot.community.model.Comment;
import com.myspringboot.community.model.User;
import com.myspringboot.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(MyErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setCommentator(Integer.valueOf(user.getAccountId()));
        commentService.insert(comment);
        return ResultDTO.successOf();
    }
}
