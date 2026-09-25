package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.repository.LivroRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {

    private final LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    // Qualquer usuário autenticado (USER ou ADMIN) pode listar os livros
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<EntityModel<Livro>> listarLivros() {
        return livroRepository.findAll().stream()
            .map(livro -> EntityModel.of(livro,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LivroController.class).obterLivro(livro.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).obterAutor(livro.getAutor().getId())).withRel("autor")
            ))
            .collect(Collectors.toList());
    }

    // Qualquer usuário autenticado (USER ou ADMIN) pode visualizar um livro específico
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public EntityModel<Livro> obterLivro(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        return EntityModel.of(livro,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LivroController.class).obterLivro(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LivroController.class).listarLivros()).withRel("todos"));
    }

    // Apenas usuários administradores (ADMIN) podem cadastrar novos livros
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Livro criarLivro(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }
}
