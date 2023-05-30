package com.example.backlogapi.core;

import java.util.UUID;

public interface ApplicationUserService {

    UUID createUser(CreateUserRequest request);
}
