package com.example.modulo_inicio_sesion.excepciones;

public class CredencialesVaciasExcepcion extends RuntimeException {
    public CredencialesVaciasExcepcion() {
        super("Las credenciales no pueden estar vacias");
    }
}
