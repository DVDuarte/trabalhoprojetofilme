package com.projetofilme.service;

import com.projetofilme.model.entity.Filme;
import com.projetofilme.model.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public List<Filme> findAll() {
        return filmeRepository.findAll();
    }

    public Filme findById(Long id) throws Exception {
        Optional<Filme> filme = filmeRepository.findById(id);
        if (!filme.isPresent()) {
            throw new Exception("Filme não encontrado");
        }
        return filme.get();
    }

    public Filme save(Filme filme) throws Exception {
        return filmeRepository.save(filme);
    }

    public Filme delete(Long id) throws Exception {
        Optional<Filme> filme = filmeRepository.findById(id);

        if (!filme.isPresent()) {
            throw new Exception("Filme não encontrado");
        }

        filmeRepository.delete(filme.get());
        return filme.get();
    }

    public Long count() {
        return filmeRepository.count();
    }

    private void validarRegrasNegocio(Filme filme) throws Exception {
        if (filme.getTitulo() == null || filme.getTitulo().length() < 3) {
            throw new Exception("Título do Filme deve ter pelo menos 3 caracteres.");
        }

        if (filmeRepository.existsByTitulo(filme.getTitulo())) {
            throw new Exception("Título do Filme deve ser único.");
        }

        if (filme.getGenero() == null || filme.getGenero().length() < 3) {
            throw new Exception("Gênero do Filme deve ter pelo menos 3 caracteres.");
        }

        if (filme.getDataLancamento() == null) {
            throw new Exception("Data de Lançamento do Filme deve ser preenchida com uma data válida.");
        }

        if (filme.getDuracao() <= 0) {
            throw new Exception("Duração do Filme deve ser maior que zero.");
        }

        if (filme.getSinopse() == null || filme.getSinopse().length() < 3) {
            throw new Exception("Sinopse do Filme deve ter pelo menos 3 caracteres.");
        }

        if (filme.getOrcamento() < 0) {
            throw new Exception("Orçamento do Filme não pode ser negativo.");
        }

        if (filme.getBilheteria() < 0) {
            throw new Exception("Bilheteria do Filme não pode ser negativa.");
        }

        if (filme.getEstudio() == null || filme.getEstudio().length() < 3) {
            throw new Exception("Estudio do Filme deve ter pelo menos 3 caracteres.");
        }

        if (filme.getAutor() == null || filme.getAutor().getNome().length() < 3) {
            throw new Exception("Nome do Autor deve ter pelo menos 3 caracteres.");
        }

        if (filme.getAutor().getDataNascimento() == null) {
            throw new Exception("Data de Nascimento do Autor deve ser preenchida com uma data válida.");
        }

        if (filme.getAutor().getBiografia() == null || filme.getAutor().getBiografia().length() < 3) {
            throw new Exception("Biografia do Autor deve ter pelo menos 3 caracteres.");
        }

        if (filme.getAutor().getWebsite() != null && !isValidURL(filme.getAutor().getWebsite())) {
            throw new Exception("O website do Autor deve ser uma URL válida.");
        }
    }

    private boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
