package com.proyecto.gestor.controllers;

import com.proyecto.gestor.dto.AuthResponseDTO;
import com.proyecto.gestor.dto.LoginDTO;
import com.proyecto.gestor.dto.RegisterDTO;
import com.proyecto.gestor.models.Roles;
import com.proyecto.gestor.models.UserEntity;
import com.proyecto.gestor.repository.RoleRepository;
import com.proyecto.gestor.repository.UserRepository;
import com.proyecto.gestor.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    //Inyeccion de dependencias
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }


    @Validated
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            //Autenticacion del usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );

            //Establecemos la autenticacion en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Genera un token JWT y lo devolvemos
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        } catch (AuthenticationException ex) {
            //Si la autentificacion falla devolvemos un http status bad request
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Validated
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername()))
            return ResponseEntity.badRequest().body("Error: Username is already taken!");

        //Creamos el nuevo usuario y configuramos sus atributos
        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));//Encriptamos la contraseña
        user.setEmail(registerDTO.getEmail());

        //Buscamos el rol de USER en la BD
        Roles roles = roleRepository.findByName("USER").get();

        //Le asignamos el rol de USER
        user.setRoles(Collections.singletonList(roles));

        //Guardamos el usuario en la base de datos
        userRepository.save(user);

        //Retornamos mensaje de exito
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    //Logout


}
