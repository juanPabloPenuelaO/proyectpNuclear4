package co.edu.cue.validacionUsuarios.interfaces;

import co.edu.cue.validacionUsuarios.model.Usuario;

public interface ConsultarPorCorreo {
    Usuario consultarPorCorreo(String email);
}
