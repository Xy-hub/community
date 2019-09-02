package com.myspringboot.community.service;

import com.myspringboot.community.dto.PaginationDTO;
import com.myspringboot.community.dto.QuestionDTO;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.exception.MyException;
import com.myspringboot.community.mapper.QuestionMapper;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.Question;
import com.myspringboot.community.model.User;
import com.myspringboot.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public PaginationDTO list(Integer page, Integer pageSize,String search) {

        Integer total=0;
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
            total = questionMapper.countBySearch(search);
        }else{
            total = questionMapper.count();
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        //总问题数

        paginationDTO.setPagination(total,page,pageSize);

        if(page<1){
            page=1;
        }else if(page>paginationDTO.getPageCount()){
            page=paginationDTO.getPageCount();
        }

        int offset=pageSize*(page-1);
        //按照指定条件获取所有问题，这里是每次获取pageSize条数据，从第offset开始
        List<Question> questionList;
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
            questionList = questionMapper.listBySearch(offset,pageSize,search);
        }else{
            questionList = questionMapper.list(offset,pageSize);
        }

        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //对象转换与属性添加
        for (Question question : questionList) {
            UserExample userExample=new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(question.getCreator().toString());
            List<User> users = userMapper.selectByExample(userExample);
            User user=users.get(0);
            QuestionDTO questionDTO = new QuestionDTO();
            //赋值属性，前面的赋值到后面的
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

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
        //取得我的问题总数
        Integer total = questionMapper.countByAccountId(accountId);
        //设置分页信息
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
            UserExample userExample=new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(question.getCreator().toString());
            List<User> users = userMapper.selectByExample(userExample);
            User user=users.get(0);
            QuestionDTO questionDTO = new QuestionDTO();
            //赋值属性
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 问题详细信息
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        //抛出自定义异常
        if(question==null){
            throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(question.getCreator().toString());
        List<User> users = userMapper.selectByExample(userExample);
        User user=users.get(0);

        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            int updated = questionMapper.update(question);
            //这里判断update!=-1是防止在修改问题是其他人删除了该问题导致的空指针异常
            //抛出自定义异常
            if(updated!=1){
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        //Question question=questionMapper.getById(id);
        Question updateQuestion=new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        //updateQuestion.setViewCount(question.getViewCount()+1);
        questionMapper.updateReply(updateQuestion);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }else{
            String[] tags = StringUtils.split(questionDTO.getTag(), ",");
            String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
            Question question = new Question();
            question.setId(questionDTO.getId());
            question.setTag(regexpTag);
            List<Question> questionList = questionMapper.selectRelated(question);
            List<QuestionDTO> questionDTOS = questionList.stream().map(q -> {
                QuestionDTO questionDTO1 = new QuestionDTO();
                BeanUtils.copyProperties(q,questionDTO1);
                return questionDTO1;
            }).collect(Collectors.toList());
            return questionDTOS;
        }
    }
}
