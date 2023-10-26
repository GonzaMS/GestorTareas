package com.proyecto.gestor.services;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.dto.UserResponse;


public interface UserService {

    //Definimos el metodo a implementar para buscar todos los usuarios
    UserResponse findAllUsers(int pageNumber, int pageSize);

    //Definimos el metodo a implementar para buscar usuario por id
    UserDTO findUserById(Long userId);

    UserDTO createUser(UserDTO userDTO);

    void deleteUser(Long userId);

}
