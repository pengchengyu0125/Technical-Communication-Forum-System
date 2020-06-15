package com.luntan.dto;

import com.luntan.model.User;
import lombok.Data;

@Data
public class PostDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private long gmtCreate;
    private long gmtModified;
    private Integer creater;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
