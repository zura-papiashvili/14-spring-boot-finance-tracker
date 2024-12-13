package com.example.finance_tracker.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {
    @NotEmpty(message = "Verification code is required")
    @Size(min = 6, max = 6, message = "Verification code should be 6 characters")
    private String verificationCode;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
