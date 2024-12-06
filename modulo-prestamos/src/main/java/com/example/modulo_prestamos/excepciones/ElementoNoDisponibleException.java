package com.example.modulo_prestamos.excepciones;

public class ElementoNoDisponibleException extends RuntimeException {
    public ElementoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}