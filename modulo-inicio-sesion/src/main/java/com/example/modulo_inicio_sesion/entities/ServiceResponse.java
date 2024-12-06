package com.example.modulo_inicio_sesion.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServiceResponse {
    private HttpStatus status;
    private String mensaje;
    private Boolean data;
}
