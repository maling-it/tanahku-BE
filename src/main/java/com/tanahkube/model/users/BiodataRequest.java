package com.tanahkube.model.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class BiodataRequest {

    @Valid

    @NotNull(message = "fullname must not be null")
    @NotBlank(message = "fullname must be not blank")
    private String fullname;

    @Pattern(regexp = "(\\d){11,13}", message = "wrong number format")
    @NotNull(message = "mobile phone must not be null")
    @NotBlank(message = "mobile phone must be not blank")
    private String mobilePhone;

    private String imagePath;
}