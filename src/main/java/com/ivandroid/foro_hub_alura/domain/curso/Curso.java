package com.ivandroid.foro_hub_alura.domain.curso;

import com.ivandroid.foro_hub_alura.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(mappedBy = "curso",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Topico> topicos;

    public Curso(DastosRegistroCurso dastosRegistroCurso) {
        this.nombre = dastosRegistroCurso.nombre();
        this.categoria = dastosRegistroCurso.categoria();
    }
}