package com.ivandroid.foro_hub_alura.controller;

import com.ivandroid.foro_hub_alura.domain.curso.Curso;
import com.ivandroid.foro_hub_alura.domain.curso.CursoRepository;
import com.ivandroid.foro_hub_alura.domain.curso.DastosRegistroCurso;
import com.ivandroid.foro_hub_alura.domain.curso.DatosRespuestaCurso;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    //Inyectando el repositorio jpa
    @Autowired
    private CursoRepository cursoRepository;

    //Endpoint para registrar un nuevo curso
    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> registrar(@RequestBody @Valid
                                               DastosRegistroCurso
                                                       dastosRegistroCurso,
                                           UriComponentsBuilder uriComponentsBuilder){
        //Guardar el nuevo curso en el repositorio
        Curso curso = cursoRepository.save(new Curso(dastosRegistroCurso));

        //Crear un objeto de respuesta con los datos del curso registrado
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(
                curso.getId(), curso.getNombre(), curso.getCategoria());

        //Construir la URI del nuevo recurso creado
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        //Devolver una respuesta con el código de estado 201 (CREATED) y el cuerpo con los datos del curso
        return  ResponseEntity.created(url).body(datosRespuestaCurso);
    }
}
