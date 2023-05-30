package com.example.springbootauthconfigured.security.jwt;

import java.io.Serializable;

public record AuthenticationResponse(String token) implements Serializable {
}
