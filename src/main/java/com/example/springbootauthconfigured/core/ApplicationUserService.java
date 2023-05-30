package com.example.springbootauthconfigured.core;

import java.util.UUID;

public interface ApplicationUserService {

    UUID createUser(CreateUserRequest request);
}
