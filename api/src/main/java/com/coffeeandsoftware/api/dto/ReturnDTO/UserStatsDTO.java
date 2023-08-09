package com.coffeeandsoftware.api.dto.ReturnDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsDTO {
    private String id;
    private String email;
    private String name;
    private String photoURL;
    private int followersCount;
    private int followingCount;
    private int posts;
    private int likes;
    private int comments;
    private String bio;
    private boolean isFollowing;
}
