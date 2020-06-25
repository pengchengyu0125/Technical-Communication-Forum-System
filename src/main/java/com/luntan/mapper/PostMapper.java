package com.luntan.mapper;

import com.luntan.dto.PostDTO;
import com.luntan.model.Post;
import com.luntan.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("insert into post (title,description,gmt_create,gmt_modified,creater,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creater},#{tag})")
    void create(Post post);

    @Select("select * from post limit #{offset},#{size}")
    List<Post> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from post")
    Integer count();

    @Select("select * from post where creater=#{userId} limit #{offset},#{size}")
    List<Post> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from post where creater=#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from post where id=#{id}")
    Post getById(@Param("id") Integer id);

    @Update("update post set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tag=#{tag} where id=#{id}")
    void update(Post post);
}
