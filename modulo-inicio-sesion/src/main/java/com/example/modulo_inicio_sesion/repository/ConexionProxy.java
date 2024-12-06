package com.example.modulo_inicio_sesion.repository;

import org.springframework.http.ResponseEntity;

public interface ConexionProxy {
    ResponseEntity<String> conectarCue(String correo, String pass);
    ResponseEntity<String> conectarValidacion(String email);
}
