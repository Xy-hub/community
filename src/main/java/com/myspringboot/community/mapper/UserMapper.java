package com.myspringboot.community.mapper;

import com.myspringboot.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(Integer accountId);

    @Update("update user set gmt_modified=#{gmtModified},token=#{token} where account_id=#{accountId}")
    void update(User dbUser);
}
