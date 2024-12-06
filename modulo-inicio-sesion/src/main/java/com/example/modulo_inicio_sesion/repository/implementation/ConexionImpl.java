package com.example.modulo_inicio_sesion.repository.implementation;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;
import com.example.modulo_inicio_sesion.excepciones.UsuarioInvalidoExcepcion;
import com.example.modulo_inicio_sesion.repository.ConexionProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;


@Component
public class ConexionImpl implements ConexionProxy {
    private final RestTemplate restTemplate;

    @Value("${auth.external.url1}")
    private String urlCUE;

    @Value("${auth.external.url2}")
    private String urlUsuario;

    public ConexionImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> conectarCue(String correo, String pass) {
        try {
            URI uri = UriComponentsBuilder.fromUri(URI.create(urlCUE))
                    .queryParam("correo", correo)
                    .queryParam("pass", pass)
                    .build().toUri();

            return restTemplate.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> conectarValidacion(String email) {
        try {
            URI uri = UriComponentsBuilder.fromUri(URI.create(urlUsuario))
                    .path("/{email}")
                    .buildAndExpand(email)
                    .toUri();

            return restTemplate.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}