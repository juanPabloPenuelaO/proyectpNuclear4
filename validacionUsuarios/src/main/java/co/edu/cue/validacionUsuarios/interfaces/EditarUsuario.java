package co.edu.cue.validacionUsuarios.interfaces;

import co.edu.cue.validacionUsuarios.model.Usuario;

public interface EditarUsuario {
    Usuario EditarUsuario(String id, String nombre, String email, String descripcion);
}
