package com.ivandroid.foro_hub_alura.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank(message = "El titulo es requerido")
        String titulo,
        @NotBlank(message = "El mensaje es requerido")
        String mensaje,
        @NotNull(message = "El id del usuario es requerido")
        Long usuarioId,
        @NotNull(message = "El id del curso es requerido")
        Long cursoId
) {
}
