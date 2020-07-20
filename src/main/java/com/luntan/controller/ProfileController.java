package com.luntan.controller;

import com.luntan.dto.PageDTO;
import com.luntan.model.User;
import com.luntan.service.NotificationService;
import com.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size){

        User user = (User)request.getSession().getAttribute("user");
        if (user==null)
            return "redirect:/";
        if ("posts".equals(action)){
            model.addAttribute("section","posts");
            model.addAttribute("sectionName","My Posts");
            PageDTO paginationDTO = postService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }
        else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","Latest Reply");
            PageDTO paginationDTO = notificationService.list(user.getId(), page, size);
            Integer unreadCount=notificationService.unreadCount(user.getId());
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
        }

        return "profile";
    }
}
