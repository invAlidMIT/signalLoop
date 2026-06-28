package com.notification.system.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notification.system.auth.enums.Role;
import com.notification.system.notification.entity.Notification;
import com.notification.system.user.enums.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public User(String email, String password,  Channel preferredChannel,String timezone,Role role) {
        this.email = email;
        this.password = password;
        this.timezone = timezone;
        this.preferredChannel = preferredChannel;
        this.role=role;
    }
}
