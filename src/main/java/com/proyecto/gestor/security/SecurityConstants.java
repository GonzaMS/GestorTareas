package com.proyecto.gestor.security;


//Clase para definir constantes del JWT
class SecurityConstants {
    static final long JWT_EXPIRATION = 70000; //Expiracion
    static final String JWT_SECRET = "gestorproyecto"; //Clave para firmar y verificar los tokens
}
