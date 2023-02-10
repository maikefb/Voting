package com.github.voting.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    @Min(1)
    private Integer page = 1;

    @Min(1)
    @Max(100)
    private Integer limit = 5;

    private String sort;
}
