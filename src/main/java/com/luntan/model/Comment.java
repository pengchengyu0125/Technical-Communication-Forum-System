package com.luntan.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private long gmtCreate;
    private long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
}
