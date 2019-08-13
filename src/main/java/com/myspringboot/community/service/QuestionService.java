package com.myspringboot.community.service;

import com.myspringboot.community.dto.PaginationDTO;
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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 问题列表
     * @param page 当前页
     * @param pageSize 每页的大小
     * @return
     */
    public PaginationDTO list(Integer page, Integer pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //总问题数
        Integer total = questionMapper.count();
        paginationDTO.setPagination(total,page,pageSize);

        if(page<1){
            page=1;
        }else if(page>paginationDTO.getPageCount()){
            page=paginationDTO.getPageCount();
        }

        int offset=pageSize*(page-1);
        //按照指定条件获取所有问题，这里是每次获取pageSize条数据，从第offset开始
        List<Question> questionList = questionMapper.list(offset,pageSize);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questionList) {
            User user=userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //赋值属性，前面的赋值到后面的
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);

        return paginationDTO;
    }

    /**
     * 我的问题列表
     * @param accountId 用户accountId
     * @param page 当前页
     * @param pageSize 每页的大小
     * @return
     */
    public PaginationDTO list(String accountId, Integer page, Integer pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer total = questionMapper.countByAccountId(accountId);
        paginationDTO.setPagination(total,page,pageSize);
        //Integer pageCount=total%pageSize==0?total/pageSize:total/pageSize+1;
        if(page<1){
            page=1;
        }else if(page>paginationDTO.getPageCount()){
            page=paginationDTO.getPageCount();
        }

        int offset=pageSize*(page-1);
        List<Question> questionList = questionMapper.listByAccountId(accountId,offset,pageSize);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questionList) {
            User user=userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //赋值属性
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);

        return paginationDTO;
    }
}
