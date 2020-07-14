package com.luntan.service;

import com.luntan.dto.CommentDTO;
import com.luntan.mapper.CommentMapper;
import com.luntan.mapper.PostMapper;
import com.luntan.mapper.UserMapper;
import com.luntan.model.Comment;
import com.luntan.model.Post;
import com.luntan.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    public void insert(Comment comment){
        if (comment.getType()==2){//回复评论
            Comment dbComment=commentMapper.getById(comment.getParentId());
            commentMapper.insert(comment);
            commentMapper.updateCommentCount(comment.getParentId());
        }
        else {//回复问题
            Post post=postMapper.getById(comment.getParentId());
            commentMapper.insert(comment);
            postMapper.updateCommentCount(post);
        }
    }

    public List<CommentDTO> listByPostId(Integer id) {
        List<Comment> comments=commentMapper.selectByParentId(id);
        if (comments.size()==0)
            return new ArrayList<>();

        //获取去重评论人
        Set<Integer> commentators=comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(commentators);

        List<User> users=new ArrayList<>();
        for (int i=0;i<userIds.size();i++){
            users.add(userMapper.findById(userIds.get(i)));
        }
        Map<Integer,User> userMap=users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS=comments.stream().map(comment -> {CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
        return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public List<CommentDTO> listByCommentId(Integer id) {
        List<Comment> comments=commentMapper.selectReply(id);
        if (comments.size()==0)
            return new ArrayList<>();

        //获取去重评论人
        Set<Integer> commentators=comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(commentators);

        List<User> users=new ArrayList<>();
        for (int i=0;i<userIds.size();i++){
            users.add(userMapper.findById(userIds.get(i)));
        }
        Map<Integer,User> userMap=users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS=comments.stream().map(comment -> {CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
