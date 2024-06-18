package com.tanahkube.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Data
public class WebResponse {
    private String status;
    private Object message;
    private Object data;
    private Object paging;
}
