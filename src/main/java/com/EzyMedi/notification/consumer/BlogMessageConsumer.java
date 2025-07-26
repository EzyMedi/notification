package com.EzyMedi.notification.consumer;

import com.EzyMedi.blog.dto.BlogMessage;
import com.EzyMedi.notification.model.Notification;
import com.EzyMedi.notification.repository.NotificationRepository;
import com.EzyMedi.notification.service.NotificationService;
import com.EzyMedi.user.data.dto.UserDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BlogMessageConsumer {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue.blog}", containerFactory = "rabbitListenerContainerFactory")
    public void consumeBlogMessage(BlogMessage message) {
        System.out.println("Received blog: " + message);

        UUID blogId = message.getBlogId();
        UUID publisherId = message.getDoctorId();
        String title = message.getTitle();
        Notification notification = new Notification(blogId, publisherId, title);
        notificationRepository.save(notification);

        List<UserDTO> followers = notificationService.getFollowers(publisherId);
        if (followers == null || followers.isEmpty()) {
            // No followers to notify
            System.out.println("No followers found for doctor " + publisherId);
            return;  // or continue without error
        }
        // Now notify them
        for (UserDTO follower : followers) {
            System.out.println("Notify follower: " + follower.getFullName() + " about blog " + message.getTitle());

        }
    }
}
