package com.ivandroid.foro_hub_alura.domain.topico.service;

import com.ivandroid.foro_hub_alura.domain.curso.Curso;
import com.ivandroid.foro_hub_alura.domain.curso.CursoRepository;
import com.ivandroid.foro_hub_alura.domain.topico.*;
import com.ivandroid.foro_hub_alura.domain.topico.validationActualizarTopico.IValidadorDeTopicosActualizar;
import com.ivandroid.foro_hub_alura.domain.topico.validationRisgistroTopicos.IValidadorDeTopicosRegistro;
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
public class DatosTopicoService {

    @Autowired
    List<IValidadorDeTopicosRegistro> validadorDeTopicosRegistro;

    @Autowired
    List<IValidadorDeTopicosActualizar> validadorDeTopicosActualizar;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    TopicoRepository topicoRepository;

    public DatosRespuestaTopico registrar(DatosRegistroTopico datos){

        validadorDeTopicosRegistro.forEach(v -> v.validar(datos));

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

    public DatosRespuestaTopico actualizar(DatosActulizarTopico datos, Long id){

        Optional<Topico> topico = topicoRepository.findById(id);

        if(topico.isPresent()){
            validadorDeTopicosActualizar.forEach(v -> v.validar(datos));
            topico.get().actulizar(datos);
            return new DatosRespuestaTopico(topico.get());
        }

        throw new IntegrityValidation("El topico no fue encontrado");

    }

    public void eliminar(Long id){

        Optional<Topico> topico = topicoRepository.findById(id);

        if(topico.isPresent()){
            topicoRepository.deleteById(id);
        } else {
            throw new IntegrityValidation("El topico no fue encontrado");
        }

    }
}
