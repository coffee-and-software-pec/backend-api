package com.coffeeandsoftware.api.validation;

import com.coffeeandsoftware.api.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentValidation {

    @Autowired
    CommentRepository commentRepository;

    public boolean validateComment(Authentication authentication, String commentId) {
        boolean result = false;
        try {
            if (authentication instanceof JwtAuthenticationToken) {
                var principal = (Jwt) authentication.getPrincipal();
                var email = principal.getClaims().get("email").toString();
                var comment = commentRepository.findById(UUID.fromString(commentId));
                result = comment
                        .map(value -> value.getAuthor().getEmail().equals(email))
                        .orElse(false);
            }
        } catch (Exception ignored) {
        }
        return result;
    }
}
