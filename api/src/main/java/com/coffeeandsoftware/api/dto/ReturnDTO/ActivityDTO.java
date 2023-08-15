package com.coffeeandsoftware.api.dto.ReturnDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    private String authorName;
    private String authorPhoto;
    private ActivityType activityType;
    private LocalDateTime createdDate;
    private String text;
}
