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
    //Inyectamos dependencias
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Metodo para buscar usuarios(usando paginacion)
    @Override
    public UserResponse findAllUsers(int pageNumber, int pageSize) {

        //Definimos la paginacion con el numero de paginas y el size de la pagina
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Buscamos los usuarios de acuerdo a la paginacion
        Page<UserEntity> users = userRepository.findAll(pageable);

        //Obtenemos el contenido de la paginacion(Users)
        List<UserEntity> usersList = users.getContent();

        //Mapeamos los usuarios a UsersDTO y los guardamos en una lista
        List<UserDTO> content = usersList.stream().map(UserServiceImpl::mapToUserDTO).collect((Collectors.toList()));

        //Instanciamos userResponse(Que es el que contiene la informacion de paginas, asi como la lista de usuarios para mostrar)
        UserResponse userResponse = new UserResponse();

        userResponse.setContent(content); //Lista de usuarios
        userResponse.setPageNumber(users.getNumber()); //Numero de pagina
        userResponse.setPageSize(users.getSize()); //Size de la pagina
        userResponse.setTotalElements(users.getTotalElements());//Total de elementos de la pagina
        userResponse.setTotalPages(users.getTotalPages());//Total de paginas
        userResponse.setLast(users.isLast());//Boolean(True si estamos en la ultima pagina, false si no)

        return userResponse;
    }


    //Metodo para buscar un user por Id
    @Override
    public UserDTO findUserById(Long userId) {
        UserEntity userEntity = userRepository.getReferenceById(userId);
        return UserServiceImpl.mapToUserDTO(userEntity);
    }


    //Metodo para crear un usuario
    @Override
    public UserDTO createUser(UserDTO userDTO) {

        //Instanciamos Userentity
        UserEntity userEntity = new UserEntity();

        //Mapeamos el username asi como el email del userDTO
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());

        //Instanciamos roles
        Roles role = new Roles();

        //Mapeamos los roles
        role.setName(userDTO.getRole());

        //Agregamos los roles
        userEntity.getRoles().add(role);

        //Guardamos el UserEntity en la BD
        userEntity = userRepository.save(userEntity);

        return UserServiceImpl.mapToUserDTO(userEntity);
    }

    //Metodo para eliminar un user
    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.getReferenceById(userId);
        userRepository.delete(userEntity);
    }


    //Convertimos un UserEntity, a un UserDTO
    private static UserDTO mapToUserDTO(UserEntity userEntity) {

        //Obtenemos los roles, si no tiene uno asignado pro defecto se le asigna(Sin Rol)
        String userRole = userEntity.getRoles()
                .stream()
                .map(Roles::getName)
                .findFirst()
                .orElse("Sin Rol");

        //Retornamos el UserDTO (Con los datos elejidos a mostrar)
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userRole)
                .build();
    }

}
