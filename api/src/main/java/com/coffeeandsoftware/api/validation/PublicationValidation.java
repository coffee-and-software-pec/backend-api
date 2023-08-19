package com.coffeeandsoftware.api.validation;

import com.coffeeandsoftware.api.repositories.PublicationRepository;
import com.coffeeandsoftware.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class PublicationValidation {

    @Autowired
    PublicationRepository publicationRepository;

    public boolean validatePublication(Authentication authentication, String publicationId) {
        boolean result = false;
        try {
            if (authentication instanceof JwtAuthenticationToken) {
                var principal = (Jwt) authentication.getPrincipal();
                var email = principal.getClaims().get("email").toString();
                var publication = publicationRepository.findById(UUID.fromString(publicationId));
                result = publication
                        .map(value -> value.getAuthor().getEmail().equals(email))
                        .orElse(false);
            }
        } catch (Exception ignored) {
        }
        return result;
    }
}
