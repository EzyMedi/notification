package com.EzyMedi.notification.MessageConsumer;

import com.EzyMedi.news.dto.NewsMessage;
import com.EzyMedi.news.service.NewsService;
import com.EzyMedi.notification.service.NotificationService;
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

    @RabbitListener(queues = "${spring.rabbitmq.queue.news}")
    public void consumeNewsMessage(NewsMessage message) {
        System.out.println("Received news: " + message);

        UUID publisherId = message.getDoctorId();
        List<User> followers = notificationService.getFollowerIds(publisherId);

        // Now notify them
        for (User follower : followers) {
            System.out.println("Notify follower: " + follower.getFullName() + " about news " + message.getTitle());
            // TODO: Save notification to DB, send WebSocket/email, etc.
        }
    }
}
