package com.luntan.mapper;

import com.luntan.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outerId,type,gmt_create,status) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status})")
    void insert(Notification notification);
}
