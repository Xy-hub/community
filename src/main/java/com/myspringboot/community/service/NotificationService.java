package com.myspringboot.community.service;

import com.myspringboot.community.dto.NotificationDTO;
import com.myspringboot.community.dto.PaginationDTO;
import com.myspringboot.community.dto.QuestionDTO;
import com.myspringboot.community.enums.NotificationEnum;
import com.myspringboot.community.enums.NotificationStatusEnum;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.exception.MyException;
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
        List<Notification> notifications = notificationMapper.listByAccountId(Integer.valueOf(accountId),offset,pageSize);
        if(notifications.size()==0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfStatus(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public int unreadCount(String accountId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(Integer.valueOf(accountId)).andStatusEqualTo(0);
        return notificationMapper.countByExample(example);
    }

    public NotificationDTO read(String id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification.getReceiver()!=Integer.parseInt(user.getAccountId())){
            throw new MyException(MyErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfStatus(notification.getType()));

        return  notificationDTO;
    }
}
