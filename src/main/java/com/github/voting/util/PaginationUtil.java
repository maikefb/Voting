package com.github.voting.util;

import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

public class PaginationUtil {

    private static final Integer FIRST_PAGE = 1;
    private static final Integer TOTAL_PAGES_WHEN_CONTENT_IS_EMPTY = 1;
    private static final Integer TOTAL_ELEMENTS_WHEN_CONTENT_IS_EMPTY = 0;

    private PaginationUtil() {
        //nothing
    }

    public static <T, U> PaginationDto<T> toPaginationDtoWithContentMapping(Page<U> resultPage, List<T> content) {
        if (isEmpty(resultPage.getContent()))
            return PaginationDto.<T>builder()
                    .content(Arrays.asList())
                    .totalPages(TOTAL_PAGES_WHEN_CONTENT_IS_EMPTY)
                    .totalElements(TOTAL_ELEMENTS_WHEN_CONTENT_IS_EMPTY)
                    .page(FIRST_PAGE)
                    .build();

        return toPaginationDto(resultPage, content);
    }

    public static Pageable toPageableWithSort(PageDto pageDto) {
        final Sort sort = getSortObject(pageDto.getSort());
        final int page = pageDto.getPage() - 1;
        return nonNull(sort) ? PageRequest.of(page, pageDto.getLimit(), sort) : PageRequest.of(page, pageDto.getLimit());
    }

    private static <T> PaginationDto<T> toPaginationDto(Page resultPage, List<T> content) {
        Integer actualPage = resultPage.getNumber() + 1;
        return PaginationDto.<T>builder()
                .content(content)
                .totalPages(resultPage.getTotalPages())
                .totalElements(resultPage.getTotalElements())
                .page(actualPage)
                .build();
    }

    private static Sort getSortObject(String sort) {
        String[] splittedSort = getSplittedSort(sort);

        if (isNull(splittedSort))
            return null;

        final String sortField = splittedSort[0];
        Sort.Direction sortDirection = ASC;
        if (splittedSort.length == 2)
            sortDirection = fromOptionalString(splittedSort[1])
                    .orElse(ASC);

        return Sort.by(sortDirection, sortField);
    }

    private static String[] getSplittedSort(String sort) {
        return ofNullable(sort)
                .filter(StringUtils::isNotEmpty)
                .map(s -> s.split(","))
                .filter(l -> l.length > 0)
                .orElse(null);
    }

}
