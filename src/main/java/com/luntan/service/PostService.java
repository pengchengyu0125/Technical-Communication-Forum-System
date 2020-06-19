package com.luntan.service;

import com.luntan.dto.PageDTO;
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

    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount=postMapper.count();
        pageDTO.setPagination(totalCount,page,size);
        if (page<1){
            page=1;
        }
        if (page>pageDTO.getTotalPage()) {
            page=pageDTO.getTotalPage();
        }

        Integer offset=size*(page-1);
        List<Post> posts = postMapper.list(offset,size);
        List<PostDTO> postDTOList = new ArrayList<>();

        for(Post post : posts){
            User user=userMapper.findById(post.getCreater());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        pageDTO.setPosts(postDTOList);

        return pageDTO;
    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount=postMapper.countByUserId(userId);
        pageDTO.setPagination(totalCount,page,size);
        if (page<1){
            page=1;
        }
        if (page>pageDTO.getTotalPage()) {
            page=pageDTO.getTotalPage();
        }

        Integer offset=size*(page-1);
        List<Post> posts = postMapper.listByUserId(userId,offset,size);
        List<PostDTO> postDTOList = new ArrayList<>();

        for(Post post : posts){
            User user=userMapper.findById(post.getCreater());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        pageDTO.setPosts(postDTOList);
        return pageDTO;
    }
}
