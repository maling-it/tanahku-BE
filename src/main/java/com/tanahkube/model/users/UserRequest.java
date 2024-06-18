package com.tanahkube.model.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserRequest {
    @Valid

    @NotBlank(message = "username must be not blank")
    private String username;

    @NotBlank(message = "email must be not blank")
    @Email
    private String email;

    @NotBlank(message = "password must be not blank")
    private String password;

    @NotNull(message = "role must be not null")
    private Long roleId;
}
