package com.myspringboot.community.controller;

import com.myspringboot.community.dto.CommentDTO;
import com.myspringboot.community.dto.QuestionDTO;
import com.myspringboot.community.enums.CommentTypeEnum;
import com.myspringboot.community.service.CommentService;
import com.myspringboot.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    //问题详情
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model){
        QuestionDTO questionDTO= questionService.getById(id);
        //System.out.println("123");
        List<CommentDTO> list=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加回复数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",list);
        return "question";
    }
}
