package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Autor;
import com.exemplo.biblioteca.repository.AutorRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
public class AutorController {

    private final AutorRepository autorRepository;

    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    // Qualquer usuário autenticado (USER ou ADMIN) pode listar os autores
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<EntityModel<Autor>> listarAutores() {
        return autorRepository.findAll().stream()
            .map(autor -> EntityModel.of(autor,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).obterAutor(autor.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).listarAutores()).withRel("autores")
            ))
            .collect(Collectors.toList());
    }

    // Qualquer usuário autenticado (USER ou ADMIN) pode visualizar um autor específico
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public EntityModel<Autor> obterAutor(@PathVariable Long id) {
        Autor autor = autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        return EntityModel.of(autor,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).obterAutor(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).listarAutores()).withRel("todos"));
    }

    // Apenas usuários administradores (ADMIN) podem cadastrar novos autores
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Autor criarAutor(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }
}
