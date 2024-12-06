package co.edu.cue.inventario.Service.Implementaciones;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.Fabricas.ElementosDtiFabrica;
import co.edu.cue.inventario.Fabricas.SeleccionFabrica;
import co.edu.cue.inventario.Interfaces.*;
import co.edu.cue.inventario.Service.ElementosService;
import co.edu.cue.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class ElementosImpl implements CrearElemento, EditarElementos, EliminarElementos, VerDetalles, CambiarEstado, ElementosService {
    private final InventarioRepository repository;
    private ElementosDti elemento;

    @Autowired
    private final SeleccionFabrica seleccionFabrica;

    public ElementosImpl(InventarioRepository repository, SeleccionFabrica seleccionFabrica) {
        this.repository = repository;
        this.seleccionFabrica = seleccionFabrica;
    }

    @PostMapping
    @Override
    public ElementosDti crearElemento(String identificacion, String nombre, String descripcion, TipoDeElementos tipo, String ubicacion){

        ElementosDtiFabrica fabrica = seleccionFabrica.obtenerFabrica(tipo);

        this.elemento = fabrica.crearElementoDti(identificacion, nombre, descripcion, tipo, EstadosElementos.DISPONIBLE, ubicacion);
        elemento.setFechaCreacion(LocalDateTime.now());
        repository.save(elemento);
        return elemento;
    }

    @GetMapping
    @Override
    public ElementosDti VerDetalles(String identificacion){
        return repository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Elemento con ID " + identificacion + " no encontrado"));
    }

    @Override
    public ElementosDti PrestarElemento(String idElemento) {
        elemento = repository.findById(idElemento)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));

        if (elemento.getEstado() != EstadosElementos.DISPONIBLE) {
            throw new NoSuchElementException("Elemento no se encuentra disponible");
        }

        // Actualiza los campos necesarios
        elemento.setEstado(EstadosElementos.EN_PRESTAMO);
        elemento.setFechaModificacion(LocalDateTime.now());

        // Guarda los cambios
        repository.save(elemento);
        return elemento;
    }

    @Override
    public ElementosDti ActivarElemento(String idElemento) {
        elemento = repository.findById(idElemento)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));

        if (elemento.getEstado() == EstadosElementos.DISPONIBLE) {
            throw new NoSuchElementException("Elemento ya se encuentra disponible");
        }

        // Actualiza los campos necesarios
        elemento.setEstado(EstadosElementos.DISPONIBLE);
        elemento.setFechaModificacion(LocalDateTime.now());

        // Guarda los cambios
        repository.save(elemento);
        return elemento;
    }

    @PutMapping
    @Override
    public ElementosDti EditarElemento(String identificacion, String nombre, String descripcion, String ubicacion){
        elemento = repository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));

        // Actualiza los campos necesarios
        elemento.setIdentificacion(identificacion);
        elemento.setNombre(nombre);
        elemento.setDescripcion(descripcion);
        elemento.setUbicacion(ubicacion);
        elemento.setFechaModificacion(LocalDateTime.now());

        // Guarda los cambios
        repository.save(elemento);
        return elemento;

    }

    @DeleteMapping
    @Override
    public ElementosDti EliminarElemento(String identificacion){
        elemento = repository.findById(identificacion)
                .orElseThrow(() -> new NoSuchElementException("Elemento no encontrado"));

        elemento.setFechaEliminacion(LocalDateTime.now());
        elemento.setEstado(EstadosElementos.NO_DISPONIBLE);

        // Guarda los cambios
        repository.save(elemento);
        return elemento;
    }

    @PutMapping
    @Override
    public ElementosDti CambiarEstado(String identificacion, EstadosElementos estado, String ubicacion){
        elemento = repository.findById(identificacion)
                .orElseThrow(()-> new NoSuchElementException("No se ha podido cambiar el estado del elemento"));

        //Se edita solamente el estado y su ubicación, cuando cambia el estado del préstamo
        elemento.setEstado(estado);
        elemento.setUbicacion(ubicacion);

        repository.save(elemento);
        return elemento;
    }

}
