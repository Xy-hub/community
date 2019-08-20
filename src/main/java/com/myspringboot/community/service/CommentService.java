package com.myspringboot.community.service;

import com.myspringboot.community.enums.CommentTypeEnum;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.exception.MyException;
import com.myspringboot.community.mapper.CommentMapper;
import com.myspringboot.community.mapper.QuestionMapper;
import com.myspringboot.community.model.Comment;
import com.myspringboot.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new MyException(MyErrorCode.TARGET_PARAM_NOT_FOUOND);
        }
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
        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());

            if(question==null){
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMapper.incComment(question);
        }
    }
}
