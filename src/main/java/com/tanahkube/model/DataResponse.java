package com.tanahkube.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataResponse {
    private Long id;
    private Double value;
    private Long deviceId;
    private Date createdOn;
    private DeviceResponse device;
}
