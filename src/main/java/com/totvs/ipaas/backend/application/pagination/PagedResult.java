package com.totvs.ipaas.backend.application.pagination;

import java.util.List;

public record PagedResult<T>(
        int page,
        int pageSize,
        Long totalElements,
        int totalPages,
        List<T> data
) {
}
