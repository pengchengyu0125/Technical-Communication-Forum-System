package com.luntan.service;

import com.luntan.dto.PostDTO;
import com.luntan.mapper.PostMapper;
import com.luntan.mapper.UserMapper;
import com.luntan.model.Post;
import com.luntan.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    public List<PostDTO> list() {
        List<Post> posts = postMapper.list();
        List<PostDTO> postDTOList = new ArrayList<>();
        for(Post post : posts){
            User user=userMapper.findById(post.getCreater());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }
}
