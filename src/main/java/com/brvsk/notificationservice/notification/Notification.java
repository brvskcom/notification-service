package com.brvsk.notificationservice.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Notification")
@Table(name = "Notification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "product_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    private String toUser;

    private String orderTrackingNumber;

    @Enumerated
    private NotificationType notificationType;

    @CreationTimestamp
    private LocalDateTime localDateTime;

    public Notification(String toUser, String orderTrackingNumber, NotificationType notificationType) {
        this.toUser = toUser;
        this.orderTrackingNumber = orderTrackingNumber;
        this.notificationType = notificationType;
    }
}

