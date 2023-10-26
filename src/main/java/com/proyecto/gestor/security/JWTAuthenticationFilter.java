package com.proyecto.gestor.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    //Inyeccion de dependencia
    @Autowired
    private JWTGenerator tokenGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    //Metodo para cada solicitud entrante
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Obtenemos el token JWT de la solicitud
        String token = JWTAuthenticationFilter.getJWTFromRequest(request);

        //Si el token es valido y se puede verificar, se procesa
        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {

            //Obtenemos el nombre de usuario del JWT token
            String username = tokenGenerator.getUsernameFromJWT(token);

            //Cargamos los detalles del usuario utilizando el customUserDetailsService
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            //Crea un objeto de autenticacion y lo establece en el contexto de seguridad
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //Continua con el flujo de la solicitud
        filterChain.doFilter(request, response);
    }


    //Metodo para obtener el token JWT de la solicitud
    private static String getJWTFromRequest(HttpServletRequest request) {
        //Obtiene el valor del encabezado "Authorization" de la solicitud
        String bearerToken = request.getHeader("Authorization");

        //Comprueba si el encabezado "Authorization" no esta vacio y si comienza con Bearer
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){

            //Si es asi, devolvemos el JWT token, eliminando el prefijo de Bearer
            return bearerToken.substring(7);
        }
        return null;
    }
}
