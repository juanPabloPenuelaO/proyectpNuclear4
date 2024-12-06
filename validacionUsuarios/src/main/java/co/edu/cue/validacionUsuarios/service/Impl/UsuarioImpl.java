package co.edu.cue.validacionUsuarios.service.Impl;

import co.edu.cue.validacionUsuarios.interfaces.*;
import co.edu.cue.validacionUsuarios.model.Usuario;
import co.edu.cue.validacionUsuarios.model.enums.Estados;
import co.edu.cue.validacionUsuarios.repository.UsuarioRepository;
import co.edu.cue.validacionUsuarios.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UsuarioImpl implements UsuarioService, AgregarUsuario, EliminarUsuario, ConsultarUsuario, EditarUsuario, ConsultarPorCorreo {

    private final UsuarioRepository repository;

    private Usuario usuario;

    public UsuarioImpl(UsuarioRepository repository) {this.repository = repository;}

    @PostMapping
    @Override
    public Usuario agregarUsuario(String id, String nombre, String email, String descripcion){
        usuario = new Usuario(id, nombre, email, Estados.ACTIVO, descripcion, LocalDateTime.now(), null, null);
        repository.save(usuario);
        return usuario;
    }

    @GetMapping
    @Override
    public Usuario consultar (String id){
        usuario = repository.findById(id).get();
        if (usuario == null || usuario.getCondicion() == Estados.INACTIVO) {
            throw new NoSuchElementException("Usuario no encontrado");
        }

        return usuario;
    }

    @GetMapping
    @Override
    public Usuario consultarPorCorreo(String email) {
        usuario = repository.findByEmail(email);
        if (usuario == null || usuario.getCondicion() == Estados.INACTIVO) {
            throw new NoSuchElementException("Usuario no encontrado");
        }
        return usuario;
    }

    @PutMapping
    @Override
    public Usuario EditarUsuario(String id, String nombre, String email, String descripcion){
        usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setDescripcion(descripcion);
        usuario.setFechaModificacion(LocalDateTime.now());

        repository.save(usuario);
        return usuario;
    }

    @DeleteMapping
    @Override
    public Usuario EliminarUsuario(String id){
        usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));
        usuario.setFechaEliminacion(LocalDateTime.now());
        usuario.setCondicion(Estados.INACTIVO);
        repository.save(usuario);
        return usuario;
    }
}
