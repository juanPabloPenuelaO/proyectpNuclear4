package com.example.modulo_prestamos.controller;

import com.example.modulo_prestamos.utils.ApiResponse;
import com.example.modulo_prestamos.entity.Prestamo;
import com.example.modulo_prestamos.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    private final ApiResponse response;

    @Autowired
    private PrestamoService prestamoService;

    public PrestamoController() {
        this.response = new ApiResponse();
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listarPrestamos() {
        try {
            List<Prestamo> prestamos = prestamoService.listarPrestamos();
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Lista de préstamos obtenida",
                    prestamos
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al listar préstamos: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo nuevoPrestamo = prestamoService.crearPrestamo(prestamo);
            if (nuevoPrestamo == null) {
                ApiResponse response = new ApiResponse(
                        HttpStatus.BAD_REQUEST,
                        "Préstamo no pudo ser creado",
                        nuevoPrestamo
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            ApiResponse response = new ApiResponse(
                    HttpStatus.CREATED,
                    "Préstamo creado exitosamente",
                    nuevoPrestamo
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al crear préstamo: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> consultarPrestamo(@PathVariable String id) {
        try {
            Optional<Prestamo> prestamo = prestamoService.consultarPrestamo(id);
            if (prestamo.isPresent()) {
                ApiResponse response = new ApiResponse(
                        HttpStatus.OK,
                        "Préstamo encontrado",
                        prestamo.get()
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(
                        HttpStatus.NOT_FOUND,
                        "Préstamo no encontrado",
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al consultar préstamo: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> modificarPrestamo(
            @PathVariable String id,
            @RequestBody Prestamo prestamo
    ) {
        try {
            Prestamo prestamoModificado = prestamoService.modificarPrestamo(id, prestamo);
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Préstamo modificado exitosamente",
                    prestamoModificado
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al modificar préstamo: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarPrestamo(@PathVariable String id) {
        try {
            prestamoService.eliminarPrestamo(id);
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Préstamo eliminado exitosamente",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al eliminar préstamo: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<ApiResponse> devolverPrestamo(@PathVariable String id) {
        try {
            prestamoService.devolverPrestamo(id);
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Préstamo devuelto exitosamente",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al devolver préstamo: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/observaciones/{prestamoId}")
    public ResponseEntity<ApiResponse> agregarObservaciones(@PathVariable String prestamoId, @RequestBody String observaciones) {
        try {
            prestamoService.agregarObservaciones(observaciones, prestamoId);
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Observaciones agregadas exitosamente",
                    null
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al agregar observaciones: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}