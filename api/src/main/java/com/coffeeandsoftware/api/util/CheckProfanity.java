package com.coffeeandsoftware.api.util;

import com.coffeeandsoftware.api.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CheckProfanity {

    @Autowired
    PublicationService publicationService;

    public ResponseEntity<?> checkProfanitiesRoutine(String text) {
        try {
            String response = publicationService.checkPublication(text);
            if (ResponseParser.hasProfanities(response)) {
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            } return null;
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
