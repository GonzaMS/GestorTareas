package com.proyecto.gestor.services.impl;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.dto.UserResponse;
import com.proyecto.gestor.models.Roles;
import com.proyecto.gestor.models.UserEntity;
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
    public UserResponse findAllUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserEntity> users = userRepository.findAll(pageable);
        List<UserEntity> usersList = users.getContent();
        List<UserDTO> content = usersList.stream().map(UserServiceImpl::mapToUserDTO).collect((Collectors.toList()));

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(content);
        userResponse.setPageNumber(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    @Override
    public UserDTO findUserById(Long userId) {
        UserEntity userEntity = userRepository.getReferenceById(userId);
        return UserServiceImpl.mapToUserDTO(userEntity);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());

        Roles role = new Roles();
        role.setName(userDTO.getRole());
        userEntity.getRoles().add(role);

        userEntity = userRepository.save(userEntity);
        return UserServiceImpl.mapToUserDTO(userEntity);
    }


    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.getReferenceById(userId);
        userRepository.delete(userEntity);
    }


    //Convertimos un UserEntity, a un UserDTO
    private static UserDTO mapToUserDTO(UserEntity userEntity) {
        String userRole = userEntity.getRoles()
                .stream()
                .map(Roles::getName)
                .findFirst()
                .orElse("Sin Rol");

        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userRole)
                .build();
    }

}
