package com.myspringboot.community.service;

import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(Integer.parseInt(user.getAccountId()));
        if(dbUser==null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
