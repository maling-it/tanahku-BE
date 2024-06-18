package com.tanahkube.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
public class DeviceRequest {
    @Valid

    private Long id;
    
    @NotBlank(message = "name must not be blank")
    @NotNull(message = "name must not be null")
    private String name;
    @NotBlank(message = "location must not be blank")
    @NotNull(message = "location must not be null")
    private String location;
    @NotBlank(message = "latitude must not be blank")
    @NotNull(message = "latitude must not be null")
    private String latitude;
    @NotBlank(message = "longitude must not be blank")
    @NotNull(message = "longitude must not be null")
    private String longitude;
}
