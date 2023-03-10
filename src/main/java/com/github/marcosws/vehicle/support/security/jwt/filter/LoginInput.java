package com.github.marcosws.vehicle.support.security.jwt.filter;

import lombok.Data;

@Data
public class LoginInput {
    private String username;
    private String password;
}
