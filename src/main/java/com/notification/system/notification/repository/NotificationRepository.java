package com.notification.system.notification.repository;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    public List<Notification> findByNotificationStatus(NotificationStatus notificationStatus);
    public List<Notification> findByUser(User user);
}
