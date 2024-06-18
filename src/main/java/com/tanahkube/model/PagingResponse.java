package com.tanahkube.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Data
public class PagingResponse {
    private Integer currentPage;
    private Integer totalPage;
    private Long totalItem;
    private Integer size;
}
