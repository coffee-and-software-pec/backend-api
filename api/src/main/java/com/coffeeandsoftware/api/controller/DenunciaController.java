package com.coffeeandsoftware.api.controller;

import java.util.List;
import java.util.UUID;
import com.coffeeandsoftware.api.services.DenunciaService;
import com.coffeeandsoftware.api.dto.DenunciaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController{

    @Autowired
    DenunciaService denunciaService; 

    @PostMapping
    public ResponseEntity<?> createDenuncia(@RequestBody DenunciaDTO denunciaDTO) {
        try {
            var d = denunciaService.createDenuncia(denunciaDTO);
            return new ResponseEntity<>(d , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null , HttpStatus.EXPECTATION_FAILED);
        }
        
    }

}