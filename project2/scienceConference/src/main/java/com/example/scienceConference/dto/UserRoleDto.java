package com.example.scienceConference.dto;

import lombok.Data;

@Data
public class UserRoleDto {
    private long userId;
    private String firstname;
    private String lastname;
    private String country;
    private String email;
    private String role;
}
