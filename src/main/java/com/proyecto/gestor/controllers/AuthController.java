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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
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

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    /**
     * public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
     * try {
     * Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
     * loginDTO.getUsername(),
     * loginDTO.getPassword()
     * )
     * );
     * <p>
     * SecurityContextHolder.getContext().setAuthentication(authentication);
     * return new ResponseEntity<>("User logged in successfully!", HttpStatus.OK);
     * } catch (AuthenticationException ex) {
     * return ResponseEntity.badRequest().body("Error: Username or password is incorrect!");
     * }
     * <p>
     * }
     **/


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername()))
            return ResponseEntity.badRequest().body("Error: Username is already taken!");

        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());

        //Buscamos el rol de usuario
        Roles roles = roleRepository.findByName("USER").get();

        //Le asignamos el rol de usuario
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }


}
