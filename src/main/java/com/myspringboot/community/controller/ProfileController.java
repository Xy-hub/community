package com.myspringboot.community.controller;

import com.myspringboot.community.dto.PaginationDTO;
import com.myspringboot.community.model.User;
import com.myspringboot.community.service.NotificationService;
import com.myspringboot.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人信息类
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){

        User user= (User) request.getSession().getAttribute("user");
        //没登录返回首页
        if(user==null){
            return "redirect:/";
        }
        //根据选择的选项设置相应的属性
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getAccountId(), page, pageSize);
            model.addAttribute("pagination",paginationDTO);
        }else if("replies".equals(action)){
            //分页查找我的问题
            PaginationDTO paginationDTO=notificationService.list(user.getAccountId(),page,pageSize);
            int unreadCount=notificationService.unreadCount(user.getAccountId());
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pagination",paginationDTO);

        }
        return "profile";
    }
}
