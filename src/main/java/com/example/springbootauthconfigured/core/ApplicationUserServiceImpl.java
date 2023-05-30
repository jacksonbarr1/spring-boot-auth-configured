package com.example.springbootauthconfigured.core;

import com.example.springbootauthconfigured.core.exception.EmailTakenException;
import com.example.springbootauthconfigured.core.exception.InvalidEmailException;
import com.example.springbootauthconfigured.core.validator.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final EmailValidator emailValidator;
    @Override
    public UUID createUser(CreateUserRequest request) {
        checkEmailValidity(request.email());
        checkEmailExists(request.email());

        if (request.password().isBlank()) {
            throw new IllegalArgumentException("Password required");
        }

        byte[] salt = createSalt();
        byte[] hash = createPasswordHash(request.password(), salt);

        return createUser(request, salt, hash);
    }

    private void checkEmailValidity(String email) {
        if (!emailValidator.checkEmailPattern(email)) {
            throw new InvalidEmailException();
        }
    }

    private void checkEmailExists(String email) {
        if (applicationUserRepository.existsByEmail(email)) {
            throw new EmailTakenException();
        }
    }

    private byte[] createSalt() {
        Random random = new SecureRandom();
        byte[] salt = new byte[128];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] createPasswordHash(String password, byte[] salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        messageDigest.update(salt);
        return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private UUID createUser(CreateUserRequest request, byte[] salt, byte[] hash) {
        ApplicationUser applicationUser = new ApplicationUser(request.email(), request.username(), salt, hash, Role.USER);
        applicationUserRepository.save(applicationUser);
        return applicationUser.getId();
    }
}
