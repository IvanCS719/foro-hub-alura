package com.ivandroid.foro_hub_alura.domain.user;

import com.ivandroid.foro_hub_alura.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correoElectronico;
    private String contrasena;
    @OneToMany(mappedBy = "autor",
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Topico> topicos;
}
