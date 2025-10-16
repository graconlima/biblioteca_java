package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Autor;
import com.exemplo.biblioteca.repository.AutorRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorRepository autorRepository;

    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @GetMapping
    public List<Autor> listar() {
        return autorRepository.findAll();
    }

    @PostMapping
    public Autor criar(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }

    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Long id) {
        return autorRepository.findById(id).orElseThrow();
    }
}
