package com.EzyMedi.notification.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue
    private UUID notificationId;
    private UUID blogId;
    private UUID doctorId;  // e.g., email or username
    private String title;           // Notification title
    private LocalDateTime timestamp;

    public Notification(UUID blogId, UUID doctorId, String title) {
        this.blogId = blogId;
        this.doctorId = doctorId;
        this.title = title;
        this.timestamp = LocalDateTime.now();
    }
}
