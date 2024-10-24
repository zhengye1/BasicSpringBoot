package com.itsp.basicspring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProDTO {
    private Long id;
    private String proName;
    private String teamName;
    private String birth;
    private String birthPlace;
    private String org;
    private Integer proYear;
}
