package co.edu.cue.validacionUsuarios.service;

import co.edu.cue.validacionUsuarios.model.Usuario;

public interface UsuarioService {
    Usuario agregarUsuario(String id, String nombre, String email, String descripcion);
    Usuario consultar (String id);
    Usuario consultarPorCorreo(String email);
    Usuario EditarUsuario(String id, String nombre, String email, String descripcion);
    Usuario EliminarUsuario(String id);
}
