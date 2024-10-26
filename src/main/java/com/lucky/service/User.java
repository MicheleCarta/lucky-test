package com.lucky.service;

import com.lucky.service.validation.ValidDateOfBirth;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    @ValidDateOfBirth
    private LocalDate dob;
}
