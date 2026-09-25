package com.exemplo.biblioteca.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login; // Nome de usuário para acessar o sistema

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    public Usuario() {}

    public Usuario(String login, String senha, Cargo cargo) {
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
    }

    // Getters e Setters comuns
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    // Métodos obrigatórios do UserDetails para o Spring Security funcionar
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mapeia o cargo para o formato padrão do Spring Security (ex: "ROLE_ADMIN")
        return List.of(new SimpleGrantedAuthority("ROLE_" + cargo.name()));
    }

    @Override 
    public String getPassword() { 
        return this.senha; 
    }

    @Override 
    public String getUsername() { 
        return this.login; 
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
