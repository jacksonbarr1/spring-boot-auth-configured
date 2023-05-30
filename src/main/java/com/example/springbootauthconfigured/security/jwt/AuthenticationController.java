package com.example.springbootauthconfigured.security.jwt;

import com.example.springbootauthconfigured.core.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.springbootauthconfigured.core.ApplicationUser;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth/authenticate")
public class AuthenticationController {

    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        ApplicationUser user;

        try {
            user = applicationConfig.authenticate(request.email(), request.password());
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }

        UserDetails userDetails = applicationConfig.loadUserByUsername(user.getEmail());

        String jwt = jwtService.generateToken(user.getEmail(), Role.USER);

        return new AuthenticationResponse(jwt);
    }
}
