package com.projetofilme.model.repository;

import com.projetofilme.model.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    boolean existsByTitulo(String titulo);
}
