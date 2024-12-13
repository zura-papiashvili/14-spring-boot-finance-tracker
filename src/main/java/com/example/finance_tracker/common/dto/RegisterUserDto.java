package com.example.finance_tracker.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
