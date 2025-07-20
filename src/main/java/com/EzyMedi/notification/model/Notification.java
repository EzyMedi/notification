package com.EzyMedi.notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;       // e.g., email or username
    private String title;           // Notification title
    private String message;         // Notification content
    private LocalDateTime timestamp;
    private boolean read;           // Whether the user has read the notification

    public Notification(String recipient, String title, String message) {
        this.recipient = recipient;
        this.title = title;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }
}
