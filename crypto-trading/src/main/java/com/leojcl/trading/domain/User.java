package com.leojcl.trading.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        Instant now = Instant.now();
        this.createAt = now;
        this.updatedAt = now;
        if(status == null){
            status = UserStatus.ACTIVE;
        }
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = Instant.now();
    }
}
