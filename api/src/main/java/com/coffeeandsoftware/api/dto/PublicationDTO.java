package com.coffeeandsoftware.api.dto;

import com.coffeeandsoftware.api.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationDTO {
    private String title;
    private String subtitle;
    private String continuous_text;
    private String main_img_url;
    private String author_id;

    private List<TagDTO> tagList;
}
