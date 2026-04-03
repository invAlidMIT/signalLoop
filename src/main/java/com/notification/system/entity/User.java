package com.notification.system.entity;

import com.notification.system.enums.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String timezone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel preferredChannel;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
