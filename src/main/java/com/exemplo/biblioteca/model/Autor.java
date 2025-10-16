package com.exemplo.biblioteca.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Livro> livros = new ArrayList<>();

    // Construtores
    public Autor() {
    }

    public Autor(String nome) {
        this.nome = nome;
    }

    public Autor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    // Métodos utilitários para gerenciar a relação bidirecional
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        livro.setAutor(this);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
        livro.setAutor(null);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
