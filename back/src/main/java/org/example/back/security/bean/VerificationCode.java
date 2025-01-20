package org.example.back.security.bean;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Ezzaim Mohammed
 **/
@Getter
@Setter
@Builder
@Entity
@Table(name = "verificationCode")
public class VerificationCode {
    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private UserDetailsImpl user;

    public VerificationCode() {
    }

    public VerificationCode(Long id, String code, LocalDateTime createdAt, LocalDateTime expiresAt, LocalDateTime validatedAt, UserDetailsImpl user) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.validatedAt = validatedAt;
        this.user = user;
    }
}
