package com.myspringboot.community.service;

import com.myspringboot.community.dto.QuestionDTO;
import com.myspringboot.community.mapper.QuestionMapper;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.Question;
import com.myspringboot.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * service层可以用来做中间层，处理mapper与controller中不能直接交互的数据。比如对象之间的转换
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            User user=userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //赋值属性
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
