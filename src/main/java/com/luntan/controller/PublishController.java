package com.luntan.controller;

import com.luntan.mapper.PostMapper;
import com.luntan.mapper.UserMapper;
import com.luntan.model.Post;
import com.luntan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null || title==""){
            model.addAttribute("error","Title cannot be empty");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","Description cannot be empty");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","Tag cannot be empty");
            return "publish";
        }

        User user=null;
        Cookie[] cookies=request.getCookies();
        if(cookies != null && cookies.length != 0)
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                user=userMapper.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setTag(tag);
        post.setCreater(user.getId());
        post.setGmtCreate(System.currentTimeMillis());
        post.setGmtModified(post.getGmtCreate());
        postMapper.create(post);
        return "redirect:/";
    }
}
