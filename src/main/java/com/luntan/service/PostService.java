package com.luntan.service;

import com.luntan.dto.PageDTO;
import com.luntan.dto.PostDTO;
import com.luntan.mapper.PostMapper;
import com.luntan.mapper.UserMapper;
import com.luntan.model.Post;
import com.luntan.model.User;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage;
        Integer totalCount=postMapper.count();
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }
        else {
            totalPage=totalCount/size+1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage) {
            page=totalPage;
        }
        pageDTO.setPagination(totalPage,page);
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
        pageDTO.setData(postDTOList);

        return pageDTO;
    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage;
        Integer totalCount=postMapper.countByUserId(userId);
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }
        else {
            totalPage=totalCount/size+1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage) {
            page=totalPage;
        }
        pageDTO.setPagination(totalPage,page);
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
        pageDTO.setData(postDTOList);
        return pageDTO;
    }

    public PostDTO getById(Integer id) {
        Post post=postMapper.getById(id);
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post,postDTO);
        User user=userMapper.findById(post.getCreater());
        postDTO.setUser(user);
        return postDTO;
    }

    public void createOrUpdate(Post post) {
        if (post.getId()==null){
            //发布
            post.setGmtCreate(System.currentTimeMillis());
            post.setGmtModified(post.getGmtCreate());
            postMapper.create(post);
        }
        else {
            //更新
            post.setGmtModified(post.getGmtCreate());
            postMapper.update(post);
        }
    }

    public void incView(Integer id) {
        Post post=postMapper.getById(id);
        postMapper.updateViewCount(post);
    }

    public List<PostDTO> selectRelated(PostDTO queryDTO) {
        if (queryDTO.getTag()==null){
            return new ArrayList<>();
        }
        String[] tags=queryDTO.getTag().split(",");
        String attachTag=tags[0];
        for (int i=1;i<tags.length;i++){
            attachTag=attachTag+"|"+tags[i];
        }
        Post post=new Post();
        post.setId(queryDTO.getId());
        post.setTag(attachTag);
        List<Post>posts=postMapper.selectRelated(post);
        List<PostDTO> postDTOS=posts.stream().map(q->{PostDTO postDTO=new PostDTO();
        BeanUtils.copyProperties(q,postDTO);
        return postDTO;
        }).collect(Collectors.toList());
        return postDTOS;
    }
}
