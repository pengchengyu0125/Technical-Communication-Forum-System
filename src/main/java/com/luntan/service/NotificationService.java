package com.luntan.service;

import com.luntan.dto.NotificationDTO;
import com.luntan.dto.PageDTO;
import com.luntan.dto.PostDTO;
import com.luntan.mapper.NotificationMapper;
import com.luntan.model.Notification;
import com.luntan.model.Post;
import com.luntan.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();
        Integer totalPage;
        Integer totalCount=notificationMapper.countByUserId(userId);
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
        List<Notification> notifications = notificationMapper.listByUserId(userId,offset,size);
        List<NotificationDTO> notifyDTOList = new ArrayList<>();

        for(Notification notification : notifications){
            User user=notificationMapper.findById(notification.getNotifier());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            if (notification.getType()==1){
                notificationDTO.setOuterTitle(notificationMapper.getPostTitle(notification.getOuterId()));
            }
            else if (notification.getType()==2){
                notificationDTO.setOuterTitle(notificationMapper.getParentTitle(notification.getOuterId()));
            }
            notificationDTO.setNotifier(user);
            notificationDTO.setNotifyName(user.getName());
            notifyDTOList.add(notificationDTO);
        }
        pageDTO.setData(notifyDTOList);
        return pageDTO;
    }

    public Integer unreadCount(Integer id) {
        return notificationMapper.unreadCount(id);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification=notificationMapper.selectById(id);
        NotificationDTO notificationDTO = new NotificationDTO();
        if (notification.getReceiver()==user.getId()){
            BeanUtils.copyProperties(notification,notificationDTO);
        }
        if (notification.getType()==2){
            notificationDTO.setOuterId(notificationMapper.getParentId(notification.getOuterId()));
        }
        notificationMapper.updateStatus(id);
        return notificationDTO;
    }
}
