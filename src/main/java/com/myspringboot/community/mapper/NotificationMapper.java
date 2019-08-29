package com.myspringboot.community.mapper;

import com.myspringboot.community.model.Notification;
import com.myspringboot.community.model.NotificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NotificationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int countByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int deleteByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int insert(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int insertSelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    List<Notification> selectByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     * @param id
     */
    Notification selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int updateByPrimaryKeySelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Thu Aug 29 08:58:02 CST 2019
     */
    int updateByPrimaryKey(Notification record);

    List<Notification> listByAccountId(@Param("accountId") Integer accountId,@Param("offset") int offset,@Param("pageSize") Integer pageSize);
}