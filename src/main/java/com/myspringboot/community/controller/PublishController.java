package com.myspringboot.community.controller;

import com.myspringboot.community.dto.QuestionDTO;
import com.myspringboot.community.mapper.QuestionMapper;
import com.myspringboot.community.model.Question;
import com.myspringboot.community.model.User;
import com.myspringboot.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,Model model){
        QuestionDTO question=questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value="id",required = false) Integer id,
            HttpServletRequest request, Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null||title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description.equals("")){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null||tag.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        //从cookie中查找用户判断是否已登录
        User user= (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        String accountId = user.getAccountId();
        question.setCreator(Integer.parseInt(accountId.trim()));
        question.setId(id);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
