package com.proyecto.gestor.dto;

import lombok.Data;

@Data
public class PageDTO {
    private String pageNumber;
    private String pageSize;
    private int totalPages;
    private int numberElements;
    private boolean isLastPage;
}
