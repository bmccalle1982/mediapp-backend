package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultDto
{
    private Integer idConsult;

    @NotNull
    private PatientDto patient;

    @NotNull
    private MedicDto medic;

    @NotNull
    private String numConsult;

    @NotNull
    private Integer idUser;

    @NotNull
    private LocalDateTime consultDate;

    @NotNull
    @JsonManagedReference
    private List<ConsultDetailDto> details;
}
