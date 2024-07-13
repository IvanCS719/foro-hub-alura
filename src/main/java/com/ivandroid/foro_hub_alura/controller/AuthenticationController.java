package com.ivandroid.foro_hub_alura.controller;

import com.ivandroid.foro_hub_alura.domain.user.DatosAutenticacionUsuario;
import com.ivandroid.foro_hub_alura.domain.user.Usuario;
import com.ivandroid.foro_hub_alura.domain.user.UsuarioRepository;
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
public class AuthenticationController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Usuario> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos){
        Authentication token = new UsernamePasswordAuthenticationToken(
                datos.correoElectronico(), datos.contrasena());
        authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
