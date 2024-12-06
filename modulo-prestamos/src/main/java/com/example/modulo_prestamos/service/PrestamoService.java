package com.example.modulo_prestamos.service;

import com.example.modulo_prestamos.entity.Prestamo;
import com.example.modulo_prestamos.entity.enums.Estado;
import com.example.modulo_prestamos.excepciones.PrestamoInvalidoException;
import com.example.modulo_prestamos.excepciones.RecursoNoDisponibleException;
import com.example.modulo_prestamos.interfaces.PrestamoBuilder;
import com.example.modulo_prestamos.interfaces.crud.*;
import com.example.modulo_prestamos.inventario_service.ConexionInventarioInterface;
import com.example.modulo_prestamos.repository.PrestamoRepository;
import com.example.modulo_prestamos.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService implements CrearObservaciones, Crear, Eliminar, Modificar, Consultar, ListarPrestamos, DevolverPrestamo {

    private final PrestamoRepository prestamoRepository;
    private final PrestamoBuilder builder;
    private final PrestamoValidationService validationService;
    private final ConexionInventarioInterface inventario;

    @Autowired
    public PrestamoService(
            PrestamoRepository prestamoRepository,
            PrestamoBuilder builder,
            PrestamoValidationService validationService,
            ConexionInventarioInterface inventario
    ) {
        this.prestamoRepository = prestamoRepository;
        this.builder = builder;
        this.validationService = validationService;
        this.inventario = inventario;
    }

    @Override
    public Optional<Prestamo> consultarPrestamo(String id) {
        return prestamoRepository.findById(id);
    }

    @Override
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo crearPrestamo(Prestamo prestamo) {
        // Valida antes de crear
        validationService.validarPrestamo(prestamo);

        // Utiliza el Builder para crear un nuevo Prestamo
        Prestamo prestamoNuevo = builder.setUsuarioId(prestamo.getUsuarioId())
                .setRecursoId(prestamo.getRecursoId())
                .setUbicacion(prestamo.getUbicacion())
                .setSede(prestamo.getSede())
                .setEstado(Estado.ACTIVO)
                .setFechaCreacion(LocalDateTime.now())
                .build();

        ApiResponse response = inventario.prestarElemento(prestamoNuevo.getRecursoId());
        if (response.getStatus() == HttpStatus.NOT_FOUND) {
            throw new RecursoNoDisponibleException("El elemento seleccionado no se encuentra disponible para prestamo");
        }

        // Retorna el Prestamo guardado en la base de dato
        return prestamoRepository.save(prestamoNuevo);
    }

    @Override
    public Prestamo modificarPrestamo(String id, Prestamo prestamo) {
        // Valida que el préstamo existe
        Prestamo prestamoConsultado = consultarPrestamo(id)
                .orElseThrow(() -> new PrestamoInvalidoException("Préstamo no encontrado"));

        // Valida los datos del préstamo
        validationService.validarPrestamo(prestamo);

        // Asigna los nuevos datos del Prestamo
        prestamoConsultado.setUsuarioId(prestamo.getUsuarioId());
        prestamoConsultado.setRecursoId(prestamo.getRecursoId());
        prestamoConsultado.setUbicacion(prestamo.getUbicacion());
        prestamoConsultado.setSede(prestamo.getSede());
        prestamoConsultado.setEstado(prestamo.getEstado());
        prestamoConsultado.setFechaModificacion(LocalDateTime.now());

        // Retorna el Prestamo modificado en la base de dato
        return prestamoRepository.save(prestamoConsultado);
    }

    @Override
    public void eliminarPrestamo(String id) {
        // Valida que el préstamo existe
        Prestamo prestamo = consultarPrestamo(id)
                .orElseThrow(() -> new PrestamoInvalidoException("Préstamo no encontrado"));

        // Asigna los datos para el soft delete del Prestamo
        prestamo.setEstado(Estado.INACTIVO);
        prestamo.setFechaEliminacion(LocalDateTime.now());

        inventario.devolverElemento(prestamo.getRecursoId());

        // Retorna el Prestamo modificado en la base de dato
        prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo agregarObservaciones(String observaciones, String prestamoId) {
        // Valida los argumentos
        validationService.validarObservaciones(observaciones, prestamoId);

        // Valida que el préstamo existe
        Prestamo prestamo = consultarPrestamo(prestamoId)
                .orElseThrow(() -> new PrestamoInvalidoException("Préstamo no encontrado"));

        // Asigna las observaciones para el Prestamo
        prestamo.setObservaciones(observaciones);

        // Retorna el Prestamo modificado en la base de dato
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo devolverPrestamo(String id) {
        // Valida que el préstamo existe
        Prestamo prestamo = consultarPrestamo(id)
                .orElseThrow(() -> new PrestamoInvalidoException("Préstamo no encontrado"));

        // Asigna los datos para el soft delete del Prestamo
        prestamo.setEstado(Estado.DEVUELTO);
        prestamo.setFechaModificacion(LocalDateTime.now());

        inventario.devolverElemento(prestamo.getRecursoId());

        // Retorna el Prestamo modificado en la base de dato
        return prestamoRepository.save(prestamo);
    }
}