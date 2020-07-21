package com.luntan.dto;

import com.luntan.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String outerTitle;
    private Integer type;
    private String notifyName;
    private Integer outerId;
}
