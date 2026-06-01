package com.notification.system.notification.audit.repository;

import com.notification.system.notification.audit.entity.NotificationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationAuditRepository extends JpaRepository<NotificationAudit,Long> {

    Optional<NotificationAudit> findByNotificationSelectionAuditId(Long id);
}
