package com.ivandroid.foro_hub_alura.controller;

import com.ivandroid.foro_hub_alura.domain.topico.DatosRegistroTopico;
import com.ivandroid.foro_hub_alura.domain.topico.DatosRespuestaTopico;
import com.ivandroid.foro_hub_alura.domain.topico.TopicoRepository;
import com.ivandroid.foro_hub_alura.domain.topico.service.DatosRegistroTopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    //Iyectando repositorio jpa
    @Autowired
    private TopicoRepository topicoRepository;

    //Iyectando el servicio para validar el registro
    @Autowired
    private DatosRegistroTopicoService datosRegistroTopicoService;

    //Endpoint para registrar un nuevo topico
    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrar(@RequestBody @Valid DatosRegistroTopico datos,
                                                          UriComponentsBuilder uriComponentsBuilder){

        //Registrando el nuevo topico
        var response = datosRegistroTopicoService.registrar(datos);

        //Construir la URI del nuevo recurso creado
        URI uri = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(response.id()).toUri();
        //Devolviendo una respuesta con el código de estado 201 (CREATED) y el cuerpo con los datos del topico
        return ResponseEntity.created(uri).body(response);
    }

    //Endpoint para obtener paginas de 10 topicos cada uno
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> mostrar(@PageableDefault(size = 10) Pageable paginacion){

        //Busqueda en la base datos para obtener una pagina de los primero 10 topicos
        var listaTopicos = topicoRepository.findAllByOrderByFechaCreacionAsc(paginacion)
                .map(DatosRespuestaTopico::new);

        //Devolviendo una respuesta con el código de estado 200 (ok) y la lista de los topicos
        return ResponseEntity.ok(listaTopicos);
    }

    //Endpoint para obtener paginas de 10 topicos con los parametros del nombre del curso y el año de publicacion del topico
    ///?curso=text&anio=numero
    @GetMapping("/")
    public ResponseEntity<Page<DatosRespuestaTopico>> mostrarPorNombreFecha(@PageableDefault(size = 10)
                                                                                Pageable paginacion,
                                                                            @RequestParam String curso,
                                                                            @RequestParam Integer anio){
        //Busqueda en la base de datos de topicos por curso y año
        var listaTopicos = topicoRepository.mostrarTopicosPorCursoYAnio(paginacion, curso, anio)
                .map(DatosRespuestaTopico::new);

        //Devolviendo una respuesta con el código de estado 200 (ok) y la lista de los topicos
        return ResponseEntity.ok(listaTopicos);
    }
}
