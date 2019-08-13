package com.myspringboot.community.controller;

import com.myspringboot.community.dto.PaginationDTO;
import com.myspringboot.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {



    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){

        //使用DTO解决mybatis级联操作
        PaginationDTO pagination=questionService.list(page,pageSize);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
