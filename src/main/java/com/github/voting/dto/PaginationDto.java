package com.github.voting.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto<T> {

    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int page;
}
