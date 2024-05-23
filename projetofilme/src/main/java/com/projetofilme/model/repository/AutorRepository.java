package com.projetofilme.model.repository;

import com.projetofilme.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
}
