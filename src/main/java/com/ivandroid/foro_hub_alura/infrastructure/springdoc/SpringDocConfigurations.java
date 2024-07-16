package com.ivandroid.foro_hub_alura.infrastructure.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                .title("Foro Hub Alura API")
                .version("1.0")
                .description("""
                        Desafio de para registrar tópicos sobre dudas de cursos de tecnoligía en una base de datos MySQL 
                        propuesto por Alura como parte del aprendizaje del Grupo 6 de Oracle Next Education, para aprender 
                        a desarrollar una API con Spring Boot, implementando conexión a una base de datos MySQL, seguridad, 
                        validaciones,endpoints y manejo de errores.
                        """)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }


}
