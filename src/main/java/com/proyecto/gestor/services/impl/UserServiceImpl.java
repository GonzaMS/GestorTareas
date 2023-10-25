package com.proyecto.gestor.services.impl;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.models.User;
import com.proyecto.gestor.repository.UserRepository;
import com.proyecto.gestor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    //Repositorio de Usuarios
    private final UserRepository userRepository;

    //Inyectamos el repositorio de Usuarios
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Metodo para buscar todos los Usuarios
    @Override
    public List<UserDTO> findAllUsers(int pageNumber, int pageSize) {
        //Creamos el objeto paginacion
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //Asignamos la paginacion y obtenemos los resultados
        Page<User> users = userRepository.findAll(pageable);
        //Obtenemos la lista de usuarios de la pagina
        List<User> usersList = users.getContent();
        //Convertimos la lista de usuarios a una lista de usuariosDTO y retornamos
        return usersList.stream().map(this::mapToUserDTO).collect((Collectors.toList()));
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return mapToUserDTO(user);
    }

    //Convertimos un User, a un UserDTO
    private UserDTO mapToUserDTO(User user){

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
