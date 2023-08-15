package com.coffeeandsoftware.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DenunciaDTO {
    private UUID publication; 
    private UUID author; 
    private String text; 
}