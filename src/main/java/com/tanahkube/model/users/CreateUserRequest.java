package com.tanahkube.model.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @Valid
    @NotNull(message = "biodata must not be null")
    private BiodataRequest biodata;

    @Valid
    @NotNull(message = "users must not be null")
    private UserRequest users;
}