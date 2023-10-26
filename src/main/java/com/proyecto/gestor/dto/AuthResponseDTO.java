package com.proyecto.gestor.dto;

import lombok.Data;

@Data
//Clase para devolver el JWT token
public class AuthResponseDTO {
    private String accessToken; //Token
    private String tokenType = "Bearer "; //Token type
    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

}
