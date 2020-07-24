package com.luntan.mapper;

import com.luntan.dto.PostDTO;
import com.luntan.model.Post;
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

    @Update("update post set view_count=view_count+1 where id=#{id}")
    void updateViewCount(Post post);

    @Update("update post set comment_count=comment_count+1 where id=#{id}")
    void updateCommentCount(Post post);

    @Select("select * from post where id!=#{id} and tag regexp #{tag}")
    List<Post> selectRelated(Post post);

    @Select("select * from post where title regexp #{search} limit #{offset},#{size}")
    List<Post> listSearch(@Param(value = "search") String search, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
}
