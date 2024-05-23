    package com.projetofilme.controller;

    import com.projetofilme.model.repository.AutorRepository;
    import com.projetofilme.model.entity.Autor;
    import com.projetofilme.model.entity.Filme;
    import com.projetofilme.model.repository.FilmeRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/filme")
    public class FilmeController {

        @Autowired
        private FilmeRepository filmeRepository;

        @Autowired
        private AutorRepository autorRepository;

        @GetMapping("/listar")
        public List<Filme> listar() {
            return filmeRepository.findAll();
        }

        @PostMapping("/incluir")
        public ResponseEntity<String> incluirFilme(@RequestBody Filme filme) {
            try {
                Optional<Autor> autorExistente = autorRepository.findByNome(filme.getAutor().getNome());

                if (autorExistente.isPresent()) {
                    filme.setAutor(autorExistente.get());
                } else {
                    Autor autorSalvo = autorRepository.save(filme.getAutor());
                    filme.setAutor(autorSalvo);
                }

                filmeRepository.save(filme);

                return ResponseEntity.status(HttpStatus.CREATED).body("Filme incluído com sucesso!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao incluir filme: " + e.getMessage());
            }
        }


        @GetMapping("/recuperar/{id}")
        public ResponseEntity<?> recuperarPeloId(@PathVariable("id") long id) {
            try {
                Optional<Filme> filme = filmeRepository.findById(id);
                if (filme.isPresent()) {
                    return ResponseEntity.ok(filme.get());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao recuperar filme: " + e.getMessage());
            }
        }

        @PutMapping("/editar/{id}")
        public ResponseEntity<String> editarFilme(@PathVariable("id") long id, @RequestBody Filme filmeAtualizado) {
            try {
                Optional<Filme> filmeExistente = filmeRepository.findById(id);
                if (filmeExistente.isPresent()) {
                    Filme filme = filmeExistente.get();
                    filme.setTitulo(filmeAtualizado.getTitulo());
                    filme.setGenero(filmeAtualizado.getGenero());
                    filme.setDataLancamento(filmeAtualizado.getDataLancamento());
                    filme.setDuracao(filmeAtualizado.getDuracao());
                    filme.setSinopse(filmeAtualizado.getSinopse());
                    filme.setOrcamento(filmeAtualizado.getOrcamento());
                    filme.setBilheteria(filmeAtualizado.getBilheteria());
                    filme.setEstudio(filmeAtualizado.getEstudio());
                    filmeRepository.save(filme);
                    return ResponseEntity.ok("Filme atualizado com sucesso!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar filme: " + e.getMessage());
            }
        }

        @DeleteMapping("/excluir/{id}")
        public ResponseEntity<String> excluirFilme(@PathVariable("id") long id) {
            try {
                Optional<Filme> filme = filmeRepository.findById(id);
                if (filme.isPresent()) {
                    filmeRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body("Filme excluído com sucesso!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir filme: " + e.getMessage());
            }
        }

        @GetMapping("/total")
        public ResponseEntity<Long> totalFilmes() {
            try {
                long total = filmeRepository.count();
                return ResponseEntity.ok(total);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
            }
        }
    }
