package com.luntan.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private double gmtCreate;
    private double gmtModified;
    private String avatarUrl;
}
