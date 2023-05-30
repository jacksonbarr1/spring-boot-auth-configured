package com.example.backlogapi.core;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class ApplicationUserController extends GenericController {

    private final ApplicationUserService applicationUserService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request) {
        UUID userId = applicationUserService.createUser(request);

        return createAPIResponse(
                HttpStatus.CREATED,
                "Successfully registered user",
                Map.of("User_Id", userId)
        );
    }
}
