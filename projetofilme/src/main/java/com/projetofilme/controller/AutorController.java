package com.projetofilme.controller;

import com.projetofilme.model.entity.Autor;
import com.projetofilme.model.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autor")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping("/listar")
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluirAutor(@RequestBody Autor autor) {
        try {
            autorRepository.save(autor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Autor incluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao incluir autor: " + e.getMessage());
        }
    }

    @GetMapping("/encontrar/{id}")
    public ResponseEntity<?> encontrarAutorPeloId(@PathVariable("id") long id) {
        try {
            Optional<Autor> autor = autorRepository.findById(id);
            if (autor.isPresent()) {
                return ResponseEntity.ok(autor.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar autor: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarAutor(@PathVariable("id") long id, @RequestBody Autor autorAtualizado) {
        try {
            Optional<Autor> autorExistente = autorRepository.findById(id);
            if (autorExistente.isPresent()) {
                Autor autor = autorExistente.get();
                autor.setNome(autorAtualizado.getNome());
                autor.setDataNascimento(autorAtualizado.getDataNascimento());
                autor.setBiografia(autorAtualizado.getBiografia());
                autor.setLocalNascimento(autorAtualizado.getLocalNascimento());
                autor.setWebsite(autorAtualizado.getWebsite());
                autorRepository.save(autor);
                return ResponseEntity.ok("Autor atualizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar autor: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirAutor(@PathVariable("id") long id) {
        try {
            Optional<Autor> autor = autorRepository.findById(id);
            if (autor.isPresent()) {
                autorRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Autor excluído com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir autor: " + e.getMessage());
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalAutores() {
        try {
            long total = autorRepository.count();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
        }
    }
}
