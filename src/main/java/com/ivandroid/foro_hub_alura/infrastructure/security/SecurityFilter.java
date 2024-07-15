package com.ivandroid.foro_hub_alura.infrastructure.security;

import com.ivandroid.foro_hub_alura.domain.user.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Obtner token del header
        var authToken = request.getHeader("Authorization");
        if (authToken != null){
            var token = authToken.replace("Bearer ", "");
            var subject = tokenService.getSubject(token);
            if(subject != null){
                //Token valido
                var usuario = usuarioRepository.findByCorreoElectronico(subject);
                var authetication = new UsernamePasswordAuthenticationToken(usuario,
                        null, usuario.getAuthorities()); //Forzar inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authetication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
