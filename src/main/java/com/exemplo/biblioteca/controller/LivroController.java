package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.repository.LivroRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    @PostMapping
    public Livro criar(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }

    /*@GetMapping("/{id}")
    public Livro buscar(@PathVariable Long id) {
        return livroRepository.findById(id).orElseThrow();
    }*/

    //aplicando HATEOAS
    @GetMapping("/{id}")
    public EntityModel<Livro> buscar(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow();
        return EntityModel.of(livro,
            linkTo(methodOn(LivroController.class).buscar(id)).withSelfRel(),
            linkTo(methodOn(AutorController.class).buscar(livro.getAutor().getId())).withRel("autor")
        );
    }
}
