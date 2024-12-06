package co.edu.cue.inventario.ElementosDti;

import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "ElementosDti")
@ToString
@Getter
@Setter
public abstract class ElementosDti {

    //Atributos generales de todos los elementos de Dti
    @Id
    protected String identificacion;
    protected String nombre;
    protected String descripcion;
    protected TipoDeElementos tipo;
    protected EstadosElementos estado;
    protected String ubicacion;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaModificacion;
    protected LocalDateTime fechaEliminacion;

    public ElementosDti(String identificacion, String nombre, String descripcion, TipoDeElementos tipo, EstadosElementos estado, String ubicacion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    //Metodos que todos los elementos deben implementar
    public abstract TipoDeElementos getTipo();

    @Override
    public String toString() {
        return super.toString();
    }
}
