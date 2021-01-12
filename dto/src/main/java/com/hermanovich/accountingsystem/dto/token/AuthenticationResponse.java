package com.hermanovich.accountingsystem.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private final String token;
}
