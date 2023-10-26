package com.proyecto.gestor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
//Clase para el login
public class LoginDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
