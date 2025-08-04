package com.totvs.ipaas.backend.infra.dtos.parameter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public record PageParameterDTO(
        @PositiveOrZero
        Integer page,
        @Min(5)
        @Max(30)
        Integer pageSize
) {
    public PageParameterDTO {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
    }
}
