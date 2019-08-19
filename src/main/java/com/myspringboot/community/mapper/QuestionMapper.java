package com.myspringboot.community.mapper;

import com.myspringboot.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> list(@Param("offset") int offset, @Param("pageSize") Integer pageSize);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{accountId} limit #{offset},#{pageSize}")
    List<Question> listByAccountId(@Param("accountId") String accountId,@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("select count(1) from question where creator=#{accountId}")
    Integer countByAccountId(String accountId);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id=#{id}")
    int update(Question question);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateReply(Question updateQuestion);
}
