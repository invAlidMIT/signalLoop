package com.notification.system.notification.scoringAlogirthm.entity;

import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FactorPercentage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Factor factor;

    private double percentage;
}
