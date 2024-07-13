package com.ivandroid.foro_hub_alura.domain.topico;

import com.ivandroid.foro_hub_alura.domain.curso.Curso;
import com.ivandroid.foro_hub_alura.domain.respuesta.Respuesta;
import com.ivandroid.foro_hub_alura.domain.user.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean estatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @OneToMany(mappedBy = "topico",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    private List<Respuesta> respuestas;


    public void actulizar(DatosActulizarTopico datos){
        if(datos.titulo() != null) this.titulo = datos.titulo();
        if(datos.mensaje() != null) this.mensaje = datos.mensaje();
    }
}
