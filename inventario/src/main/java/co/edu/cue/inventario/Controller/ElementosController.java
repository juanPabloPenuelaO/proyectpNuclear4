package co.edu.cue.inventario.Controller;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Requests.RequestElementos;
import co.edu.cue.inventario.Requests.RespuestaApi;
import co.edu.cue.inventario.Service.ElementosService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/elementos")
public class ElementosController {
    private final ElementosService service;

    public ElementosController(ElementosService service) {
        this.service = service;
    }


    @PostMapping("/registrar-elemento")
    public ResponseEntity<RespuestaApi<ElementosDti>> crearElemento(@Valid @RequestBody RequestElementos request) {
        // Llama al servicio con los datos mapeados desde el cuerpo de la solicitud
        try {
            ElementosDti elementoCreado = service.crearElemento(
                    request.getIdentificacion(),
                    request.getNombre(),
                    request.getDescripcion(),
                    request.getTipo(),
                    request.getUbicacion()
                    );
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento creado exitosamente",
                    elementoCreado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al crear el elemento",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/actualizar-elemento/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> ActualizarElemento(
            @PathVariable("id") String identificacion,
            @RequestBody RequestElementos request) {
        try {
            ElementosDti actualizado = service.EditarElemento(
                    identificacion,
                    request.getNombre(),
                    request.getDescripcion(),
                    request.getUbicacion()
            );
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    actualizado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/consultar-elemento/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> verElemento(
            @PathVariable("id") String id) {
        try {
            ElementosDti elementosDti = service.VerDetalles(id);
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Esta es la información del elemento",
                    elementosDti // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/eliminar-elemento/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> deleteElement(
            @PathVariable("id") String id) {
        try {
            ElementosDti eliminado = service.EliminarElemento(id);
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    eliminado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/actualizar-estado/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> CambiarEstado(
            @PathVariable("id") String id,
            @RequestBody RequestElementos request) {
        try {
           ElementosDti actualizado = service.CambiarEstado(
                   id,
                    request.getEstado(),
                    request.getUbicacion()
                    );
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    actualizado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/prestar-elemento/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> PrestarElemento(@PathVariable("id") String id) {
        try {
            ElementosDti actualizado = service.PrestarElemento(id);
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento prestado exitosamente",
                    actualizado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al prestar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PutMapping("/activar-elemento/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> ActivarElemento(@PathVariable("id") String id) {
        try {
            ElementosDti actualizado = service.ActivarElemento(id);
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento prestado exitosamente",
                    actualizado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al prestar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
