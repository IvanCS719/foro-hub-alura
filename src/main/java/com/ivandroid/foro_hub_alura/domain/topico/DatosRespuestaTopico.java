package com.ivandroid.foro_hub_alura.domain.topico;

import com.ivandroid.foro_hub_alura.domain.respuesta.Respuesta;

import java.time.LocalDateTime;
import java.util.List;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean estatus,
        String autor,
        String curso,
        List<Respuesta> respuestas
) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion(), topico.getEstatus(), topico.getAutor().getNombre(),
                topico.getCurso().getNombre(), topico.getRespuestas());
    }
}
