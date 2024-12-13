package com.example.finance_tracker.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {
    private String verificationCode;

    private String email;
}
