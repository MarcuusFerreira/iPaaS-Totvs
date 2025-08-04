package com.totvs.ipaas.backend.infra.dtos.response;

import com.totvs.ipaas.backend.application.pagination.PagedResult;

import java.util.List;

public record PaginationResponseDTO(
        int page,
        int pageSize,
        Long totalElements,
        int totalPages,
        List<?> data
) {
    public static PaginationResponseDTO fromPagedResult(PagedResult pagedResult) {
        return new PaginationResponseDTO(
                pagedResult.page(),
                pagedResult.pageSize(),
                pagedResult.totalElements(),
                pagedResult.totalPages(),
                pagedResult.data()
        );
    }
}
