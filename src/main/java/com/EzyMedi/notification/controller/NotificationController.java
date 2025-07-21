package com.EzyMedi.notification.controller;

import com.EzyMedi.news.model.News;
import com.EzyMedi.notification.model.Notification;
import com.EzyMedi.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get")
    List<Notification> getAllNews() {
        return notificationService.getAllNotifications();
    }
}
