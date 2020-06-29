package com.luntan.mapper;

import com.luntan.model.Comment;
import com.luntan.model.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,gmt_create,gmt_modified,commentator,content,like_count) values (#{parentId},#{type},#{gmtCreate},#{gmtModified},#{commentator},#{content},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment getById(@Param("id") Integer id);
}
