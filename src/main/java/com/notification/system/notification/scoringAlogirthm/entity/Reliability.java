package com.notification.system.notification.scoringAlogirthm.entity;

import com.notification.system.user.enums.Channel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reliability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private double value;

}
