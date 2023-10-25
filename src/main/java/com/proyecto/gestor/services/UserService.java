package com.proyecto.gestor.services;

import com.proyecto.gestor.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    //Metodo para buscar todos los usuarios
    List<UserDTO> findAllUsers();
}
