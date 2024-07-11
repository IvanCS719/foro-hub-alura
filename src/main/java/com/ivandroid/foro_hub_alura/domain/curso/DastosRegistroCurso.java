package com.ivandroid.foro_hub_alura.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DastosRegistroCurso(
        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombre,
        @NotNull(message = "Debes elegir una categoria")
        Categoria categoria
) {
}
