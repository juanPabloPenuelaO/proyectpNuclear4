package com.example.modulo_inicio_sesion.controller;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;
import com.example.modulo_inicio_sesion.repository.implementation.ConexionProxyImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class InicioSesionController {

    private final ConexionProxyImpl conexionProxy;

    public InicioSesionController(ConexionProxyImpl conexionProxy) {
        this.conexionProxy = conexionProxy;
    }

    @GetMapping("/inicio-sesion")
    public ResponseEntity<ServiceResponse> login(
            @RequestParam String correo,
            @RequestParam String pass) {

        // Validación de usuario
        ResponseEntity<String> responseValidacion = conexionProxy.conectarValidacion(correo);
        ServiceResponse serviceResponseValidacion = new ServiceResponse();

        if (responseValidacion.getStatusCode() != HttpStatus.NOT_FOUND) {
            serviceResponseValidacion.setStatus(HttpStatus.BAD_REQUEST);
            serviceResponseValidacion.setMensaje("El usuario se encuentra inactivo");
            return ResponseEntity.badRequest().body(serviceResponseValidacion);
        }

        // Conexión CUE
        ResponseEntity<String> response = conexionProxy.conectarCue(correo, pass);
        ServiceResponse serviceResponse = new ServiceResponse();

        if (response.getStatusCode() != HttpStatus.FOUND) {
            serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMensaje("El usuario no pudo ser encontrado");
            return ResponseEntity.badRequest().body(serviceResponse);
        }

        // Éxito
        serviceResponse.setStatus(HttpStatus.OK);
        serviceResponse.setMensaje("Autenticación exitosa");
        serviceResponse.setData(true);
        return ResponseEntity.ok(serviceResponse);
    }
}