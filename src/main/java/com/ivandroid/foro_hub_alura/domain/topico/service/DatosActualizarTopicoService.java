package com.ivandroid.foro_hub_alura.domain.topico.service;

import com.ivandroid.foro_hub_alura.domain.curso.CursoRepository;
import com.ivandroid.foro_hub_alura.domain.topico.*;
import com.ivandroid.foro_hub_alura.domain.topico.validationActualizarTopico.IValidadorDeTopicosActualizar;
import com.ivandroid.foro_hub_alura.domain.topico.validationRisgistroTopicos.IValidadorDeTopicosRegistro;
import com.ivandroid.foro_hub_alura.domain.user.UsuarioRepository;
import com.ivandroid.foro_hub_alura.infrastructure.error.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatosActualizarTopicoService {

    @Autowired
    List<IValidadorDeTopicosActualizar> validadorDeTopicos;

    @Autowired
    TopicoRepository topicoRepository;

    public DatosRespuestaTopico actualizar(DatosActulizarTopico datos, Long id){

        Optional<Topico> topico = topicoRepository.findById(id);

        if(topico.isPresent()){
            validadorDeTopicos.forEach(v -> v.validar(datos));
            topico.get().actulizar(datos);
            return new DatosRespuestaTopico(topico.get());
        }

        throw new IntegrityValidation("El topico no fue encontrado");

    }
}
