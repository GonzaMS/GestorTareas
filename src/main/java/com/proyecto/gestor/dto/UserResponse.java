package com.proyecto.gestor.dto;

import lombok.Data;

import java.util.List;

@Data
//Clase para la paginacion de usuarios
public class UserResponse {
    private List<UserDTO> content; //Lista de Users
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
