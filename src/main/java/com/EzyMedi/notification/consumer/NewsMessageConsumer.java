package com.EzyMedi.notification.consumer;

import com.EzyMedi.news.dto.NewsMessage;
import com.EzyMedi.notification.model.Notification;
import com.EzyMedi.notification.repository.NotificationRepository;
import com.EzyMedi.notification.service.NotificationService;
import com.EzyMedi.user.data.dto.UserDTO;
import com.EzyMedi.user.data.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class NewsMessageConsumer {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue.news}", containerFactory = "rabbitListenerContainerFactory")
    public void consumeNewsMessage(NewsMessage message) {
        System.out.println("Received news: " + message);

        UUID newsId = message.getNewsId();
        UUID publisherId = message.getDoctorId();
        String title = message.getTitle();
        Notification notification = new Notification(newsId, publisherId, title);
        notificationRepository.save(notification);

        List<UserDTO> followers = notificationService.getFollowers(publisherId);
        if (followers == null || followers.isEmpty()) {
            // No followers to notify
            System.out.println("No followers found for doctor " + publisherId);
            return;  // or continue without error
        }
        // Now notify them
        for (UserDTO follower : followers) {
            System.out.println("Notify follower: " + follower.getFullName() + " about news " + message.getTitle());

        }
    }
}
