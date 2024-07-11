package com.ivandroid.foro_hub_alura.controller;

import com.ivandroid.foro_hub_alura.domain.topico.DatosRegistroTopico;
import com.ivandroid.foro_hub_alura.domain.topico.DatosRespuestaTopico;
import com.ivandroid.foro_hub_alura.domain.topico.Topico;
import com.ivandroid.foro_hub_alura.domain.topico.TopicoRepository;
import com.ivandroid.foro_hub_alura.domain.topico.service.DatosRegistroTopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    //Inyectar repositorio jpa
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private DatosRegistroTopicoService datosRegistroTopicoService;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrar(@RequestBody @Valid
                                                          DatosRegistroTopico datos,
                                                          UriComponentsBuilder uriComponentsBuilder){

        var response = datosRegistroTopicoService.registrar(datos);

        URI uri = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
