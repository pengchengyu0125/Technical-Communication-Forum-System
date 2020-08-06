package com.luntan.schedule;

import com.luntan.dto.HotTopicDTO;
import com.luntan.mapper.PostMapper;
import com.luntan.model.Post;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTopicTasks {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private HotTopicDTO hotTopicDTO;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        int offset=0;
        int limit=20;
        log.info("The time is now {}", new Date());
        List<Post> list=new ArrayList<>();
        Map<String, Integer> priorities= new HashMap<>();
        while (offset==0||list.size()==limit){
            list=postMapper.list(offset,limit);
            for (Post post:list){
                String[] tags= post.getTag().split(",");
                for (String tag:tags){
                    Integer priority=priorities.get(tag);
                    if (priority!=null){
                        priorities.put(tag,priority+5+post.getCommentCount());
                    }
                    else {
                        priorities.put(tag,5+post.getCommentCount());
                    }
                }
            }
            offset+=limit;
        }
        hotTopicDTO.updateTags(priorities);
    }
}
