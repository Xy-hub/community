package com.myspringboot.community.service;

import com.myspringboot.community.mapper.UserMapper;
import com.myspringboot.community.model.User;
import com.myspringboot.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        if(users.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            User dbUser=users.get(0);

            User record = new User();
            record.setGmtModified(System.currentTimeMillis());
            record.setToken(user.getToken());
            UserExample userExample=new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(dbUser.getAccountId());
            userMapper.updateByExampleSelective(record,userExample);
        }
    }
}
