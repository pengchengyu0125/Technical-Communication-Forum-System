package com.luntan.mapper;

import com.luntan.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,gmt_create,gmt_modified,commentator,content,like_count) values (#{parentId},#{type},#{gmtCreate},#{gmtModified},#{commentator},#{content},#{likeCount})")
    void insert(Comment comment);
}
