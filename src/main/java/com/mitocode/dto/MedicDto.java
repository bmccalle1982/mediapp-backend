package com.mitocode.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicDto
{
    private Integer idMedic;

    @NotNull
    private Integer idSpecialty;

    @NotNull
    @Size(min = 3)
    private String primaryName;

    @NotNull
    @Size(min = 3)
    private String surname;

    @NotNull
    @Size(min = 3, max = 12)
    private String cmpMedic;

    @NotNull
    private String photo;
}
