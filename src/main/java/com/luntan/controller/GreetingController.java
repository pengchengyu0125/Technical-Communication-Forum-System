package com.luntan.controller;

import com.luntan.dto.PostDTO;
import com.luntan.mapper.PostMapper;
import com.luntan.mapper.UserMapper;
import com.luntan.model.Post;
import com.luntan.model.User;
import com.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GreetingController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies=request.getCookies();
        if(cookies != null && cookies.length != 0)
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                User user=userMapper.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        List<PostDTO> postList = postService.list();
        model.addAttribute("posts",postList);
        return "index";
    }
}