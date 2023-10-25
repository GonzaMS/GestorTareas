package com.proyecto.gestor.controllers;

import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.models.User;
import com.proyecto.gestor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {
    //Referencia al servicio
    private final UserService userService;

    //Inyectamos el servicio
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Obtener todos los usuarios
    @GetMapping("/users")
    public List<UserDTO> listUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize

    ) {
        return userService.findAllUsers(pageNumber, pageSize);
    }

    //Obtener usuario por id
    @GetMapping("/users/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

}
