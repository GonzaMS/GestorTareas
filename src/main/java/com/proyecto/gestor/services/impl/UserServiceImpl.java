package com.proyecto.gestor.services.impl;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.models.User;
import com.proyecto.gestor.repository.UserRepository;
import com.proyecto.gestor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //Metodo para buscar todos los Usuarios
    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDTO(user)).collect((Collectors.toList()));
    }

    //Convertimos un User, a un UserDTO
    private UserDTO mapToUserDTO(User user){
        UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return userDTO;
    }
}
