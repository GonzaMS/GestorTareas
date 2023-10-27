package com.proyecto.gestor.controllers;

import com.proyecto.gestor.config.HttpService;
import com.proyecto.gestor.dto.UserDTO;
import com.proyecto.gestor.dto.UserResponse;
import com.proyecto.gestor.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    //Inyeccion de dependencias
    private final HttpService httpService;
    private final IUserService IUserService;
    @Autowired
    public UserController(HttpService httpService, IUserService IUserService) {
        this.httpService = httpService;
        this.IUserService = IUserService;
    }

    //Crear usuario
    @PostMapping("users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(IUserService.createUser(userDTO), HttpStatus.CREATED);
    }

    //Obtener todos los usuarios
    @GetMapping("users")
    public ResponseEntity<UserResponse> listUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2",required = false) int pageSize
    ) {
        return new ResponseEntity<>(IUserService.findAllUsers(pageNumber, pageSize), HttpStatus.OK);
    }

    //Obtener usuario por id
    @GetMapping("users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(IUserService.findUserById(userId), HttpStatus.OK);
    }


    //Editar un usuario
    @PutMapping("users/{userId}")
    public ResponseEntity<UserDTO> putUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        IUserService.updateUser(userId, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Borrar un usuario
    @DeleteMapping("users/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
        IUserService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users/remote")
    public String getDataFromRemoteApi() {
        try {
            return httpService.fetchDataFromApi();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";

        }
    }
}
