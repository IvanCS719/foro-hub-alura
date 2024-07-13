package com.ivandroid.foro_hub_alura.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosActulizarTopico(
        @NotBlank(message = "El titulo es requerido")
        String titulo,
        @NotBlank(message = "El mensaje es requerido")
        String mensaje
) {
}
