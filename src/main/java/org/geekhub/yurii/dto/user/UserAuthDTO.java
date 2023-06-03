package org.geekhub.yurii.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthDTO {

    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
