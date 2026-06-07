package com.notification.system.notification.reliabilityMetrics.entity;

import com.notification.system.user.enums.Channel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "channel_metrics")
@Getter
@Setter
@NoArgsConstructor
public class ChannelMetrics {

    @Id
    @Enumerated(value = EnumType.STRING)
    private Channel channel;

    private long successCount;
    private long failureCount;
    private long retryCount;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;
}
