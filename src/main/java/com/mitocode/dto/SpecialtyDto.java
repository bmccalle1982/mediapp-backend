package com.mitocode.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyDto
{
    private Integer idSpecialty;

    @NotNull
    private String nameSpecialty;

    @NotNull
    private String descriptionSpecialty;


}
