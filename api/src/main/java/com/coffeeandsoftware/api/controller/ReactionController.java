package com.coffeeandsoftware.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coffeeandsoftware.api.dto.ReactionDTO;
import com.coffeeandsoftware.api.services.ReactionService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/reaction")
public class ReactionController {
    
    @Autowired
    ReactionService reactionService;

    @PostMapping
    public ResponseEntity<?> createReaction(@RequestBody ReactionDTO reactionDTO) {
//        Reaction reaction = reactionService.createReaction(reactionDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
