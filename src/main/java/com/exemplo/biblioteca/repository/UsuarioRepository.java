package com.exemplo.biblioteca.repository;

import com.exemplo.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca no banco usando o campo 'login' que criamos em português
    Optional<Usuario> findByLogin(String login); 
}
