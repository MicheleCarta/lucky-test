package com.lucky.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucky.service.validation.ValidDateOfBirth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;

public record UserDTO(
        @JsonProperty("email")
        @NotBlank(message = "Email must not be blank")
        @Size(max = 255, message = "Email must be less than 255 characters")
        @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Email must be valid")
        String email,
        @JsonProperty("firstName")
        @NotBlank(message = "First name must not be blank")
        @Size(max = 128, message = "First name must be less than 128 characters")
        String firstName,
        @JsonProperty("lastName")
        @Size(max = 255, message = "Last name must be less than 128 characters")
        String lastName,
        @JsonProperty("dob")
        @JsonFormat(pattern="yyyy-MM-dd")
        @NotNull(message = "Date of birth is required")
        @ValidDateOfBirth(message = "User must be at least 18 years old")
        LocalDate dob

) {
        public int age() {
                return Period.between(dob, LocalDate.now()).getYears();
        }
}
