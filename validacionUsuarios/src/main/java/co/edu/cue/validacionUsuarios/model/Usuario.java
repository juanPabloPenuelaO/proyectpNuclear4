package co.edu.cue.validacionUsuarios.model;

import co.edu.cue.validacionUsuarios.model.enums.Estados;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ListaNegraUsuarios")
@ToString
@Getter
@Setter
public class Usuario {

    @Id
    private String id;
    private String nombre;
    private String email;
    private Estados condicion ;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaEliminacion;

    public Usuario(String id, String nombre, String email, Estados condicion, String descripcion, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion, LocalDateTime fechaEliminacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.condicion = condicion;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.fechaEliminacion = fechaEliminacion;
    }
}
