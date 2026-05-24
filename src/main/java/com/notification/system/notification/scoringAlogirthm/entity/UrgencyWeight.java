package com.notification.system.notification.scoringAlogirthm.entity;

import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import com.notification.system.user.enums.Channel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UrgencyWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private double urgencyPercentage;

}
