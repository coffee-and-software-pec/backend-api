package com.coffeeandsoftware.api.validation;

import com.coffeeandsoftware.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserValidation {

    @Autowired
    UserRepository userRepository;

    public boolean validateAuthenticatedUserId(Authentication authentication, String userId) {
        boolean result = false;
        try {
            if (authentication instanceof JwtAuthenticationToken) {
                var principal = (Jwt) authentication.getPrincipal();
                var email = principal.getClaims().get("email").toString();
                var user = userRepository.findByEmail(email);
                result = user
                    .map(value -> value.getU_id().equals(UUID.fromString(userId)))
                    .orElse(false);
            }
        } catch (Exception ignored) {
        }
        return result;
    }

    public boolean validateAuthenticatedUserEmail(Authentication authentication, String userEmail) {
        boolean result = false;
        try {
            if (authentication instanceof JwtAuthenticationToken) {
                var principal = (Jwt) authentication.getPrincipal();
                var email = principal.getClaims().get("email").toString();
                var user = userRepository.findByEmail(email);
                result = user
                        .map(value -> value.getEmail().equals(userEmail))
                        .orElse(false);
            }
        } catch (Exception ignored) {
        }
        return result;
    }
}
