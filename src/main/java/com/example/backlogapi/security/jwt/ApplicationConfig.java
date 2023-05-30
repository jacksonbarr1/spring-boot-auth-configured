package com.example.backlogapi.security.jwt;

import com.example.backlogapi.core.ApplicationUser;
import com.example.backlogapi.core.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationConfig implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + email)
                );
    }

    public ApplicationUser authenticate(String email, String password) throws NoSuchAlgorithmException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new BadCredentialsException("Unauthorized");
        }

        Optional<ApplicationUser> applicationUser = applicationUserRepository.findByEmail(email);

        if (applicationUser.isEmpty()) {
            throw new BadCredentialsException("Unauthorized");
        }

        boolean verified = verifyPasswordHash(password,
                applicationUser.get().getHash(),
                applicationUser.get().getSalt());

        if (!verified) {
            throw new BadCredentialsException("Unauthorized");
        }

        return applicationUser.get();
    }

    private boolean verifyPasswordHash(String password, byte[] hash, byte[] salt) throws NoSuchAlgorithmException {
        if (password.isBlank() || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be empty or whitespace only string.");

        if (hash.length != 64) {
            throw new IllegalArgumentException("Invalid length of password hash (64 bytes expected)");
        }

        if (salt.length != 128) {
            throw new IllegalArgumentException("Invalid length of password salt (64 bytes expected).");
        }

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt);

        var computedHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        for (int i = 0; i < computedHash.length; i++) {
            if (computedHash[i] != hash[i]) return false;
        }

        return MessageDigest.isEqual(computedHash, hash);
    }
}
