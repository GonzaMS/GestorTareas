package com.proyecto.gestor.security;

import com.proyecto.gestor.models.Roles;
import com.proyecto.gestor.models.UserEntity;
import com.proyecto.gestor.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //Inyeccion de dependencias
    private final IUserRepository IUserRepository;
    @Autowired
    public CustomUserDetailsService(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }

    //Metodo para cargar los detalles del usuario por su username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Busca un usuario por username en el repositorio
        UserEntity user = IUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Crea un objeto UserDetails utilizando el username, password y roles
        return new User(user.getUsername(), user.getPassword(), CustomUserDetailsService.mapRolesToAuthorities(user.getRoles()));
    }


    //Metodo para mapear roles a autoridades
    private static Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles) {

        //Convierte la lista de roles en una lista de autoridades
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
