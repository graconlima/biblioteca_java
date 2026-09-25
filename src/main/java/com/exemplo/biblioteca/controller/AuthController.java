package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.dto.AutenticacaoRequisicao;
import com.exemplo.biblioteca.dto.AutenticacaoResposta;
import com.exemplo.biblioteca.dto.RegistroRequisicao;
import com.exemplo.biblioteca.model.Usuario;
import com.exemplo.biblioteca.repository.UsuarioRepository;
import com.exemplo.biblioteca.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, 
                          PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody RegistroRequisicao requisicao) {
        if (usuarioRepository.findByLogin(requisicao.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Este login já está sendo usado!");
        }

        Usuario usuario = new Usuario(
                requisicao.getLogin(),
                passwordEncoder.encode(requisicao.getSenha()), // Criptografa a senha antes de salvar
                requisicao.getCargo()
        );
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/entrar")
    public ResponseEntity<AutenticacaoResposta> entrar(@RequestBody AutenticacaoRequisicao requisicao) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requisicao.getLogin(), requisicao.getSenha())
        );
        
        String token = jwtService.generateToken(requisicao.getLogin());
        return ResponseEntity.ok(new AutenticacaoResposta(token));
    }
}
