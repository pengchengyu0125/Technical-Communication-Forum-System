package com.luntan.mapper;

import com.luntan.model.Post;
import com.luntan.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("insert into post (title,description,gmt_create,gmt_modified,creater,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creater},#{tag})")
    void create(Post post);

    @Select("select * from post")
    List<Post> list();
}
