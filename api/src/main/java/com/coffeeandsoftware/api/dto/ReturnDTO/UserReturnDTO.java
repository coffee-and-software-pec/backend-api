package com.coffeeandsoftware.api.dto.ReturnDTO;

import com.coffeeandsoftware.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnDTO {
    private String u_id;
    private String u_name;
    private String photoURL;

    public UserReturnDTO(User user) {
        this.u_id = user.getU_id().toString();
        this.u_name = user.getU_name();
        this.photoURL = user.getPhotoURL();
    }
}
