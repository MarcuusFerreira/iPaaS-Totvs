package com.totvs.ipaas.backend.infra.dtos.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorDTO(
        int status,
        String title,
        String detail,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ssXXX", timezone = "America/Sao_Paulo")
        OffsetDateTime timestamp,
        String path,
        List<Field> fields
) {
}
