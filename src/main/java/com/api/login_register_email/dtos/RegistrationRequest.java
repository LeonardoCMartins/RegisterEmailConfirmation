package com.api.login_register_email.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@ToString
public class RegistrationRequest {
    private final String username;
    private final String email;
    private final String password;
}
