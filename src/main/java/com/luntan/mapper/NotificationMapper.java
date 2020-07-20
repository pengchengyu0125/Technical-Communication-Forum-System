package com.luntan.mapper;

import com.luntan.model.Notification;
import com.luntan.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outerId,type,gmt_create,status) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver=#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from notification where receiver=#{userId} limit #{offset},#{size}")
    List<Notification> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select title from post where id=#{outerId}")
    String getPostTitle(@Param("outerId") Integer outerId);

    @Select("select title from post where id=(select parent_id from comment where id=#{outerId})")
    String getParentTitle(@Param("outerId") Integer outerId);

    @Select("select count(1) from notification where receiver=#{id} and status=0")
    Integer unreadCount(@Param("id") Integer id);
}
