package com.example.securenote.domain.user.entity;

import com.example.securenote.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EnableMethodSecurity
@Entity
@Table(name = "users", indexes = @Index(name = "ux_users_email", columnList = "email", unique = true))
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
}
