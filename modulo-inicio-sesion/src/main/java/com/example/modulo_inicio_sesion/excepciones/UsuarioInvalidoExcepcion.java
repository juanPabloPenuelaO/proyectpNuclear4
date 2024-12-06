package com.example.modulo_inicio_sesion.excepciones;

public class UsuarioInvalidoExcepcion extends RuntimeException {
    public UsuarioInvalidoExcepcion() {
        super("El usuario no tiene cuenta activa en el sistema");
    }
}
