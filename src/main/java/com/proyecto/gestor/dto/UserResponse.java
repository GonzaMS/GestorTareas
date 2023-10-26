package com.proyecto.gestor.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private List<UserDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
