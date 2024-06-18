package com.tanahkube.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class DataRequest {
    @Valid

    @NotNull(message = "device id cannot be null")
    private Long deviceId;
    @NotNull(message = "value cannot be null")
    private Double value;
}
