package com.coffeeandsoftware.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationUpdateDTO {
    private String title;
    private String subtitle;
    private String continuous_text;
    private String main_img_url;
}
