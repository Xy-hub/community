package com.myspringboot.community.mapper;

import com.myspringboot.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);

    @Select("select * from user where account_id=#{accountId} limit 1")
    User findByAccountId(Integer accountId);
}
