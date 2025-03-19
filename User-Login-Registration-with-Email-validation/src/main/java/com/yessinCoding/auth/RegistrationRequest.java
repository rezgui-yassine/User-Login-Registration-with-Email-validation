package com.yessinCoding.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is mandatory")
    private  String firstName;
    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email is mandatory")
    @Email(message = "email is invalid  ")
    private String email;
    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, max = 20, message = "password must be between 8 and 20 characters")
    private String password;

}
