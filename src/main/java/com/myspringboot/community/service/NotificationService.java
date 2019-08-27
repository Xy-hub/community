package com.myspringboot.community.service;

import com.myspringboot.community.dto.NotificationDTO;
import com.myspringboot.community.dto.PaginationDTO;
import com.myspringboot.community.dto.QuestionDTO;
import com.myspringboot.community.mapper.NotificationMapper;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(String accountId, Integer page, Integer pageSize) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //取得我的问题总数
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(Integer.valueOf(accountId));
        Integer total = notificationMapper.countByExample(example);
        //设置分页信息
        paginationDTO.setPagination(total,page,pageSize);
        //Integer pageCount=total%pageSize==0?total/pageSize:total/pageSize+1;
        if(page<1){
            page=1;
        }else if(page>paginationDTO.getPageCount()){
            page=paginationDTO.getPageCount();
        }

        int offset=pageSize*(page-1);
        List<Notification> notifications = notificationMapper.listByAccountId(accountId,offset,pageSize);
        if(notifications.size()==0){
            return paginationDTO;
        }

        Set<Integer> disUserAccountIds=notifications.stream().map(notify->notify.getNotifier()).collect(Collectors.toSet());
        ArrayList<Integer> userAccountId = new ArrayList<>(disUserAccountIds);
        ArrayList<String> userAccountIds = new ArrayList<>();
        for(Integer id:userAccountId){
            String str=id.toString();
            userAccountIds.add(str);
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userAccountIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getAccountId(), u -> u));

        List<NotificationDTO> notificationDTOS=new ArrayList<>();

        return paginationDTO;
    }
}
