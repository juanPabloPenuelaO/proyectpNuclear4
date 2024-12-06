package co.edu.cue.validacionUsuarios.controller;

import co.edu.cue.validacionUsuarios.model.Usuario;
import co.edu.cue.validacionUsuarios.request.RespuestaApi;
import co.edu.cue.validacionUsuarios.request.UsuarioRequest;
import co.edu.cue.validacionUsuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RespuestaApi<Usuario>> agregarUsuario(@Valid @RequestBody UsuarioRequest request){
        try{
           Usuario usuario = service.agregarUsuario(
                   request.getId(),
                   request.getNombre(),
                   request.getEmail(),
                   request.getDescripcion()
           );
           RespuestaApi<Usuario> response = new RespuestaApi<>(
                    HttpStatus.CREATED,
                    "Usuario creado exitosamente",
                    usuario
           );
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al crear el usuario: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaApi<Usuario>> ActualizarElemento(
            @PathVariable("id") String id,
            @RequestBody UsuarioRequest request) {
        try {
            Usuario usuario = service.EditarUsuario(
                    id,
                    request.getNombre(),
                    request.getEmail(),
                    request.getDescripcion()
            );
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    HttpStatus.OK,
                    "Usuario actualizado exitosamente",
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }  catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el usuario: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<Usuario>> consultarUsuario(@PathVariable("id") String id) {
        try {
            Usuario usuario = service.consultar(id);
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    HttpStatus.FOUND,
                    "El usuario se encuentra estado activo",
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar al usuario: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/correo/{email}")
    public ResponseEntity<RespuestaApi<Usuario>> consultarUsuarioPorCorreo(@PathVariable("email") String email) {
        try {
            Usuario usuario = service.consultarPorCorreo(email);
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    HttpStatus.FOUND,
                    "El usuario se encuentra estado activo",
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar al usuario: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaApi<Usuario>> deleteElement(@PathVariable("id") String id) {
        try {
            Usuario usuario = service.EliminarUsuario(id);
            RespuestaApi<Usuario> response = new RespuestaApi<>(
                    HttpStatus.OK,
                    "Usuario eliminado exitosamente",
                    usuario // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<Usuario> errorResponse = new RespuestaApi<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el usuario: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
