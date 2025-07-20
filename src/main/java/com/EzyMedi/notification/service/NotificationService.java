package com.EzyMedi.notification.service;

import com.EzyMedi.notification.repository.NotificationRepository;
import com.EzyMedi.user.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.get.all.followers.url}")
    String getFollowersUrl;

    public List<User> getFollowerIds(UUID doctorId) {
        String url = getFollowersUrl + doctorId;

        // This assumes the response is a list of user IDs (Long)
        List<User> followers = restTemplate.getForObject(url, List.class);
        return followers;
    }

}
