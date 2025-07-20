package com.EzyMedi.notification.service;

import com.EzyMedi.notification.model.Notification;
import com.EzyMedi.notification.repository.NotificationRepository;
import com.EzyMedi.user.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.get.all.followers.url}")
    String getFollowersUrl;

    public List<User> getFollowers(UUID doctorId) {
        String url = getFollowersUrl + doctorId;

        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        log.info("Response: {}", response.getBody());
        return response.getBody();
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

}
