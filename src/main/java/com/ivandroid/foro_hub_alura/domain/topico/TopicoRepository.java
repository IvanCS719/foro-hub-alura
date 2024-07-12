package com.ivandroid.foro_hub_alura.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface TopicoRepository extends JpaRepository<Topico,Long> {

    //Comprobar si el titulo del topico ya esta registrado
    Boolean existsByTituloIgnoreCase(String titulo);

    //Comprobar si el mensaje del topico ya esta registrado
    Boolean existsByMensajeIgnoreCase(String titulo);

    //Obtener una pagina con los topico ordenados por fecha de forma ascendete
    Page<Topico> findAllByOrderByFechaCreacionAsc(Pageable paginacion);

    //Obtner una pagina con los topicos de cierto curso y año publicado
    @Query("""
        SELECT t FROM Topico t JOIN t.curso c
        WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :curso, '%')) AND YEAR(t.fechaCreacion) = :anio
        """)
    Page<Topico> mostrarTopicosPorCursoYAnio(Pageable paginacion, @Param("curso") String curso, @Param("anio") Integer anio);

}
