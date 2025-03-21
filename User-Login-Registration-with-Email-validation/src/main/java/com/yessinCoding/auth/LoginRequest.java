package com.yessinCoding.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    @Email(message = "Email is not Formatted")
    @NotEmpty(message = "Email is  mandatory")
    @NotBlank(message="email is mandatory")
    private String email;
    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, max = 20, message = "password must be between 8 and 20 characters")
    private String password;
}
