package co.edu.cue.inventario.Fabricas;

import co.edu.cue.inventario.Enums.TipoDeElementos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SeleccionFabrica {
    private  final Map<TipoDeElementos, ElementosDtiFabrica> fabricas;

    @Autowired
    public SeleccionFabrica(List<ElementosDtiFabrica> fabricasList) {
        // Ahora la variable 'fabricas' está bien definida como un Map
        this.fabricas = fabricasList.stream()
                .collect(Collectors.toMap(
                        fabrica -> {
                            if (fabrica instanceof FabricaDeAccesorios) return TipoDeElementos.ACCESORIO;
                            if (fabrica instanceof FabricaDeAudiovisual) return TipoDeElementos.AUDIOVISUAL;
                            if (fabrica instanceof FabricaDeControles) return TipoDeElementos.CONTROL;
                            if (fabrica instanceof FabricaDeComputadores) return TipoDeElementos.COMPUTADOR;
                            throw new IllegalArgumentException("Unknown factory type");
                        },
                        Function.identity()
                ));
    }


    public  ElementosDtiFabrica obtenerFabrica(TipoDeElementos tipo) {
        ElementosDtiFabrica fabrica = this.fabricas.get(tipo);
        if (fabrica == null) {
            throw new IllegalArgumentException("No existe fábrica para el tipo: " + tipo);
        }
        return fabrica;
    }
}
