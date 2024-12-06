package com.example.modulo_inicio_sesion.repository.implementation;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;
import com.example.modulo_inicio_sesion.excepciones.CredencialesVaciasExcepcion;
import com.example.modulo_inicio_sesion.repository.ConexionProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConexionProxyImpl implements ConexionProxy {
    private final ConexionImpl conexion;

    public ConexionProxyImpl(ConexionImpl conexion) {
        this.conexion = conexion;
    }

    @Override
    public ResponseEntity<String> conectarCue(String correo, String pass) {
        if (!correo.isEmpty() && !pass.isEmpty()) {
            correo = correo.replaceAll("[';\"\\-\\-]", "");
            pass = pass.replaceAll("[';\"\\-\\-]", "");
            return conexion.conectarCue(correo, pass);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las credenciales no pueden estar vacias");
        }
    }

    @Override
    public ResponseEntity<String> conectarValidacion(String correo) {
        if (!correo.isEmpty()) {
            correo = correo.replaceAll("[';\"\\-\\-]", "");
            return conexion.conectarValidacion(correo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo no puede estar vacio");
        }
    }

}
