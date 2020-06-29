package com.luntan.service;

import com.luntan.mapper.CommentMapper;
import com.luntan.mapper.PostMapper;
import com.luntan.model.Comment;
import com.luntan.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    public void insert(Comment comment){
        if (comment.getType()==1){
            Comment dbComment=commentMapper.getById(comment.getParentId());
            commentMapper.insert(comment);
        }
        else {
            Post post=postMapper.getById(comment.getParentId());
            commentMapper.insert(comment);
            postMapper.updateCommentCount(post);
        }
    }
}
