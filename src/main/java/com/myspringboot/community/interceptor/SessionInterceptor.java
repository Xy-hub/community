package com.myspringboot.community.interceptor;

import com.myspringboot.community.mapper.NotificationMapper;
import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.User;
import com.myspringboot.community.model.UserExample;
import com.myspringboot.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 定义拦截器，这里主要是拦截登录信息
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得Cookie信息
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie!=null&&cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(example);
                    if(users.size()>0){
                        request.getSession().setAttribute("user",users.get(0) );
                        //通知数
                        int unreadCount = notificationService.unreadCount(users.get(0).getAccountId());
                        request.getSession().setAttribute("unreadMessage",unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
