package com.example.springbootauthconfigured.security.jwt;

import java.io.Serializable;

public record AuthenticationRequest(String email, String password) implements Serializable {
}
