package com.ivandroid.foro_hub_alura.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    Boolean existsByTituloIgnoreCase(String titulo);


    Boolean existsByMensajeIgnoreCase(String titulo);
}
