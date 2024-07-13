package com.ivandroid.foro_hub_alura.domain.topico.validationActualizarTopico;

import com.ivandroid.foro_hub_alura.domain.topico.DatosActulizarTopico;
import com.ivandroid.foro_hub_alura.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Regla de negocio
@Component
public class DatosRepetidosActualizar implements IValidadorDeTopicosActualizar {

    //Inyeccion del repositorio jpa de Topico
    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosActulizarTopico datosActulizarTopico) {

        //Se busca en la base de datos si existe un titulo similar
        Boolean tituloRepetido = topicoRepository
                .existsByTituloIgnoreCase(datosActulizarTopico.titulo());

        //Si esta duplicando retornamos una excepcion
        if (tituloRepetido) {
            throw new  RuntimeException("Este título ya esta publicado");
        }

        //Se busca en la base de datos si existe un mensaje similar
        Boolean mensajeRepetido = topicoRepository
                .existsByMensajeIgnoreCase(datosActulizarTopico.mensaje());

        //Si esta duplicando retornamos una excepcion
        if (mensajeRepetido){
            throw new  RuntimeException("Este mensaje es igual a uno que ya esta publicado");
        }
    }
}
