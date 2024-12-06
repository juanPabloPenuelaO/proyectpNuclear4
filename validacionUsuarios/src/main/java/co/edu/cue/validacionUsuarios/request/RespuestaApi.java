package co.edu.cue.validacionUsuarios.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RespuestaApi <T>{
    private HttpStatus estado;
    private String mensaje;
    private T datos;

    public RespuestaApi(HttpStatus estado, String mensaje, T datos) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.datos = datos;
    }
}
