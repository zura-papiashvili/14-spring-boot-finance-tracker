package com.example.finance_tracker.modules.user.dto;

import com.example.finance_tracker.modules.user.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarName;
    private String avatarUrl;
    private Role role;
    private String createdAt;
    private String updatedAt;

}
