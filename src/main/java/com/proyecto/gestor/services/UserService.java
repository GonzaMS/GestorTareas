package com.proyecto.gestor.services;

import com.proyecto.gestor.dto.UserDTO;

import java.util.List;


public interface UserService {

    //Definimos el metodo a implementar para buscar todos los usuarios
    List<UserDTO> findAllUsers();

    //Definimos el metodo a implementar para buscar usuario por id
    UserDTO findUserById(Long userId);

}
