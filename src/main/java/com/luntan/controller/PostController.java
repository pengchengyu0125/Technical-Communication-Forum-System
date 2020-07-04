package com.luntan.controller;

import com.luntan.dto.CommentCreateDTO;
import com.luntan.dto.CommentDTO;
import com.luntan.dto.PostDTO;
import com.luntan.service.CommentService;
import com.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    public PostController() {
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable(name="id") Integer id,
                       Model model){
        PostDTO postDTO=postService.getById(id);
        List<CommentDTO> comments=commentService.listByPostId(id);
        //记录阅读数
        postService.incView(id);
        model.addAttribute("post",postDTO);
        model.addAttribute("comments",comments);
        return "post";
    }
}
