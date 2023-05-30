package com.example.backlogapi.security.jwt;

import java.io.Serializable;

public record AuthenticationRequest(String email, String password) implements Serializable {
}
