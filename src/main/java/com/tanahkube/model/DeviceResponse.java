package com.tanahkube.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceResponse {
    private Long id;
    private String name;
    private String apiKey;
    private String location;
    private String latitude;
    private String longitude;
}
