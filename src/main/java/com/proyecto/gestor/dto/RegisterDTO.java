package com.proyecto.gestor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
//Clase de register
public class RegisterDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
}
