package com.pimentelprojects.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data

public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "El campo nombre no puede estar vacío.")
    private String name;
    @NotEmpty(message = "El campo correo no puede estar vacío.")
    @Email(message = "Por favor ingrese un correo electrónico válido.")
    private String email;
    @NotEmpty(message = "El campo dirección no puede estar vacío.")
    private String address;
    @NotEmpty(message = "El campo contraseña no puede estar vacío.")
    private String password;
}
