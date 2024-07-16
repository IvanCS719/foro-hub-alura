package com.ivandroid.foro_hub_alura.controller;

import com.ivandroid.foro_hub_alura.domain.user.DatosAutenticacionUsuario;
import com.ivandroid.foro_hub_alura.domain.user.Usuario;
import com.ivandroid.foro_hub_alura.domain.user.UsuarioRepository;
import com.ivandroid.foro_hub_alura.infrastructure.security.DatosJWTToken;
import com.ivandroid.foro_hub_alura.infrastructure.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacion", description = "obtiene el token para el usuario asignado que da acceso al resto de endpoint")
public class AuthenticationController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datos.correoElectronico(), datos.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
