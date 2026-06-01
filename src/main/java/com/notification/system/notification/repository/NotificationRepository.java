package com.notification.system.notification.repository;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    public List<Notification> findByNotificationStatus(NotificationStatus notificationStatus);
    public List<Notification> findByUser(User user);

    @Query("""
            select n from Notification n
            join fetch n.user where n.notificationId=:id
            """
    )
    public Optional<Notification> findByIdWithUser(@Param("id") Long id);
}
