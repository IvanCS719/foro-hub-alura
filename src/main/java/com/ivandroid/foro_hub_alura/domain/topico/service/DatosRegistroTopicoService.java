package com.ivandroid.foro_hub_alura.domain.topico.service;

import com.ivandroid.foro_hub_alura.domain.curso.Curso;
import com.ivandroid.foro_hub_alura.domain.curso.CursoRepository;
import com.ivandroid.foro_hub_alura.domain.topico.DatosRegistroTopico;
import com.ivandroid.foro_hub_alura.domain.topico.DatosRespuestaTopico;
import com.ivandroid.foro_hub_alura.domain.topico.Topico;
import com.ivandroid.foro_hub_alura.domain.topico.TopicoRepository;
import com.ivandroid.foro_hub_alura.domain.topico.validation.IValidadorDeTopicos;
import com.ivandroid.foro_hub_alura.domain.user.Usuario;
import com.ivandroid.foro_hub_alura.domain.user.UsuarioRepository;
import com.ivandroid.foro_hub_alura.infrastructure.error.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatosRegistroTopicoService {

    @Autowired
    List<IValidadorDeTopicos> validadorDeTopicos;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    TopicoRepository topicoRepository;

    public DatosRespuestaTopico registrar(DatosRegistroTopico datos){

        validadorDeTopicos.forEach(v -> v.validar(datos));

        Usuario usuario = buscarUsuario(datos.usuarioId());

        Curso curso = buscarCurso(datos.cursoId());

        Topico topico = new Topico(null, datos.titulo(), datos.mensaje(), LocalDateTime.now(),
                false, usuario, curso, new ArrayList<>());

        topicoRepository.save(topico);

        return new DatosRespuestaTopico(topico);
    }

    private Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) return usuario.get();

        throw new IntegrityValidation("El Usuario no fue encontrado");
    }

    private Curso buscarCurso(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if(curso.isPresent()) return  curso.get();

        throw new IntegrityValidation("El Curso no fue encontrado");
    }
}
