package com.mitocode.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto
{
    private Integer idPatient;

    @NotNull//El campo no puede olvidarme del atributo
    //@NotEmpty //El campo no puede estar asi "", valida que el atributo no este vacio
    //@NotBlank//El campo no puede estar en blanco, que no haya caracteres en blanco
    @Size(min = 3, max = 70, message = "{firstname.size}")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 7, message = "{lastname.size}")
    private String lastName;

    @NotNull
    private String dni;

    @NotNull
    private String address;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    private String phone;

    @NotNull
    @Email
    private String email;
}
