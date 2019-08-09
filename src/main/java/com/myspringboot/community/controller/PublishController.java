package com.myspringboot.community.controller;

import com.myspringboot.community.mapper.QuestionMapper;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.Question;
import com.myspringboot.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    QuestionMapper questionMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag, HttpServletRequest request, Model model){

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
        User user=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie!=null&&cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    //System.out.println(token);
                    user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user );
                    }
                    break;
                }
            }
        }
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
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
