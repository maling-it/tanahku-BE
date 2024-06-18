package com.tanahkube.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BiodataResponse {
    private String fullname;
    private String mobilePhone;
    private String imagePath;
}