package com.ivandroid.foro_hub_alura.controller;

import com.ivandroid.foro_hub_alura.domain.topico.*;
import com.ivandroid.foro_hub_alura.domain.topico.service.DatosActualizarTopicoService;
import com.ivandroid.foro_hub_alura.domain.topico.service.DatosRegistroTopicoService;
import com.ivandroid.foro_hub_alura.infrastructure.error.IntegrityValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    //Iyectando repositorio jpa
    @Autowired
    private TopicoRepository topicoRepository;

    //Iyectando el servicio para validar el registro
    @Autowired
    private DatosRegistroTopicoService datosRegistroTopicoService;

    @Autowired
    private DatosActualizarTopicoService datosActualizarTopicoService;

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

    //Enpoint para obtener la informacion de un topico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> mostrarUnTopico(@PathVariable Long id){
        //Buscando el topico por el id
        Optional<Topico> topico = topicoRepository.findById(id);

        //Se verifica si el topico existe
        if (topico.isPresent()){
            //Se tranforma la entidad topico al record DatosRespuestaTopico
            var datosTopico = new DatosRespuestaTopico(topico.get());
            //Devolviendo una respuesta con el código de estado 200 (ok) y con los datos topicos
            return ResponseEntity.ok(datosTopico);
        }

        //Si no existe se lanza una exepcion y un mensaje
        throw new IntegrityValidation("El topico no fue encontrado");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizar(@RequestBody @Valid DatosActulizarTopico datos,
                           @PathVariable Long id){

        var response = datosActualizarTopicoService.actualizar(datos, id);

        return ResponseEntity.ok().body(response);

    }
}
