package co.edu.cue.validacionUsuarios.interfaces;

import co.edu.cue.validacionUsuarios.model.Usuario;

public interface AgregarUsuario {
    Usuario agregarUsuario(String id, String nombre, String email, String descripcion);
}
