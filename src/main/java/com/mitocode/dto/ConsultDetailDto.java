package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultDetailDto
{
    private Integer idDetail;

    @JsonBackReference
    private ConsultDto consult;

    @NotNull
    private String diagnosis;

    @NotNull
    private String treatment;
}
