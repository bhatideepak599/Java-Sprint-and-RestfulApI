package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String firstName;

    private String lastName;
    private boolean active=true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
