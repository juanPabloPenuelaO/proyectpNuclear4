package co.edu.cue.validacionUsuarios.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class UsuarioRequest {
    @Id
    private String id;
    @NotNull(message = "El usuario debe tener un nombre")
    private String nombre;
    @NotNull(message = "El usuario debe tener un correo")
    private String email;
    @NotNull(message = "El usuario debe tener una descripcion")
    private String descripcion;
}
