package com.luntan.dto;

import com.luntan.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private long gmtCreate;
    private long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}

