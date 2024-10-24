package com.itsp.basicspring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamDTO {
    private Long id;
    private String name;
    private List<ProDTO> pros;
}
