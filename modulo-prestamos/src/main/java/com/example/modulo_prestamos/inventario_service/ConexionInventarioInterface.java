package com.example.modulo_prestamos.inventario_service;

import com.example.modulo_prestamos.utils.ApiResponse;

public interface ConexionInventarioInterface {
    ApiResponse validarDisponibilidad(String recursoId);
    ApiResponse devolverElemento(String recursoId);
    ApiResponse prestarElemento(String recursoId);
}
