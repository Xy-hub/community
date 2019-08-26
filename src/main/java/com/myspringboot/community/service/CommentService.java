package com.myspringboot.community.service;

import com.myspringboot.community.dto.CommentDTO;
import com.myspringboot.community.enums.CommentTypeEnum;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.exception.MyException;
import com.myspringboot.community.mapper.CommentExtMapper;
import com.myspringboot.community.mapper.CommentMapper;
import com.myspringboot.community.mapper.QuestionMapper;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentExtMapper commentExtMapper;

    //插入回复，做出许多表单验证
    @Transactional      //使用注解，当出现异常时会自动回滚，也就是回到方法运行之前的状态
    public void insert(Comment comment) {
        //未选中问题进行回复
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new MyException(MyErrorCode.TARGET_PARAM_NOT_FOUOND);
        }
        //评论类型错误或不存在
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new MyException(MyErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new MyException(MyErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Comment parentComment = new Comment();
            //更新上一级评论的回复数
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCouont(parentComment);
        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question==null){
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            //插入到回复表
            commentMapper.insert(comment);
            question.setCommentCount(1);
            //更新问题回复数
            questionMapper.incComment(question);
        }
    }

    public List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if(comments.size()==0){
            return null;
        }
        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(commentators);
        List<String> userAccountIds=new ArrayList<>();
        for(Integer userId:userIds){
            String userAccountId=userId.toString();
            userAccountIds.add(userAccountId);
        }
        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userAccountIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));
        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator().toString()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
