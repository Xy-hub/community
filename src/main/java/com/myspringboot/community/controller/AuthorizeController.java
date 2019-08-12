package com.myspringboot.community.controller;

import com.myspringboot.community.dto.AccessTokenDto;
import com.myspringboot.community.dto.GithubUser;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.User;
import com.myspringboot.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    //自动注入
    @Autowired
    private GithubProvider githubProvider;

    //@Value从配置文件中读取数据
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){

        //dto类用于转换多个参数到一个类中，减少参数量,相当于模仿bean作为参数
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRediect_uri(redirectUri);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //git中的数据
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            //将user对象放进session中
            //request.getSession().setAttribute("user",githubUser);
            //登录成功，写cookie和session
            response.addCookie(new Cookie("token",token));
            //重定向到‘/’请求
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }

    }
}
