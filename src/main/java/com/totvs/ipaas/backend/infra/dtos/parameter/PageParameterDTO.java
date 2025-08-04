package com.totvs.ipaas.backend.infra.dtos.parameter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public record PageParameterDTO(
        @PositiveOrZero(message = "Only 0 and higher numbers are accepted")
        Integer page,
        @Min(value = 5, message = "The minimum page size is 5")
        @Max(value = 30, message = "The maximum page size is 30")
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
