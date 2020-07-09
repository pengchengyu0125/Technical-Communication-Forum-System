package com.luntan.controller;

import com.luntan.dto.CommentCreateDTO;
import com.luntan.dto.CommentDTO;
import com.luntan.dto.ResultDTO;
import com.luntan.model.Comment;
import com.luntan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO){
        Comment comment=new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setCommentator(commentCreateDTO.getCommentator());
        commentService.insert(comment);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id){
        List<CommentDTO> commentDTOS=commentService.listByCommentId(id);
        return ResultDTO.ok(commentDTOS);
    }
}
