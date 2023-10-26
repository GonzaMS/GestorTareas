package com.proyecto.gestor.controllers;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.dto.UserResponse;
import com.proyecto.gestor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    //Referencia al servicio
    private final UserService userService;

    //Inyectamos el servicio
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Obtener todos los usuarios
    @GetMapping("users")
    public ResponseEntity<UserResponse> listUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(userService.findAllUsers(pageNumber, pageSize), HttpStatus.OK);
    }

    //Obtener usuario por id
    @GetMapping("users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    //Crear usuario
    @PostMapping("users/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }


    //Borrar un usuario
    @DeleteMapping("users/delete/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
