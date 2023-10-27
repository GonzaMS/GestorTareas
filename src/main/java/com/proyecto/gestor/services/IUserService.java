package com.proyecto.gestor.services;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.dto.UserResponse;


public interface IUserService {

    //Metodo para mostrar todos los usuarios(con parametros de paginacion)
    UserResponse findAllUsers(int pageNumber, int pageSize);

    //Metodo para buscar un user en especifico
    UserDTO findUserById(Long userId);

    //Metodo para crear usuario
    UserDTO createUser(UserDTO userDTO);


    //Update user
    UserDTO updateUser(Long userId, UserDTO userDTO);

    //Metodo para borrar usuario
    void deleteUser(Long userId);


}
