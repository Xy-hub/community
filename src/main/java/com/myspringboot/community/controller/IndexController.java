package com.myspringboot.community.controller;

import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        //从cookie中查找用户判断是否已登录
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie!=null&&cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    //System.out.println(token);
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user );
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
