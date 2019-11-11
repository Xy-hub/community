package com.myspringboot.community.controller;

import com.myspringboot.community.dto.NotificationDTO;
import com.myspringboot.community.dto.PaginationDTO;
import com.myspringboot.community.enums.NotificationEnum;
import com.myspringboot.community.mapper.NotificationMapper;
import com.myspringboot.community.model.User;
import com.myspringboot.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 通知信息类
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") String id,HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        //没登录返回首页
        if(user==null){
            return "redirect:/";
        }
        //已读
        NotificationDTO notificationDTO=notificationService.read(id,user);
        //判断通知类型
        if(NotificationEnum.REPLY_QUESTION.getStatus()==notificationDTO.getType()||
                NotificationEnum.REPLY_COMMENT.getStatus()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else{
            return "redirect:/";
        }

    }
}
