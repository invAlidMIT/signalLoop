package com.notification.system.notification.repository;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    public List<Notification> findNotificationBynotificationStatus(NotificationStatus notificationStatus);
}
