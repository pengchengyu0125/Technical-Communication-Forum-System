package com.luntan.controller;

import com.luntan.dto.CommentDTO;
import com.luntan.dto.NotificationDTO;
import com.luntan.dto.PostDTO;
import com.luntan.model.User;
import com.luntan.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(HttpServletRequest httpServletRequest, @PathVariable(name="id") Integer id){
        User user=(User) httpServletRequest.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(id,user);
        return "redirect:/post/"+notificationDTO.getOuterId();
    }
}
