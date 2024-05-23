package com.projetofilme.service;

import com.projetofilme.model.entity.Autor;
import com.projetofilme.model.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Autor findById(Long id) throws Exception {
        Optional<Autor> autor = autorRepository.findById(id);
        if (!autor.isPresent()) {
            throw new Exception("Autor não encontrado");
        }
        return autor.get();
    }

    public Autor save(Autor autor) throws Exception {
        validarRegrasNegocio(autor);
        return autorRepository.save(autor);
    }

    public Autor delete(Long id) throws Exception {
        Optional<Autor> autor = autorRepository.findById(id);

        if (!autor.isPresent()) {
            throw new Exception("Autor não encontrado");
        }

        autorRepository.delete(autor.get());
        return autor.get();
    }

    public Long count() {
        return autorRepository.count();
    }

    private void validarRegrasNegocio(Autor autor) throws Exception {
        if (autor.getNome() == null || autor.getNome().length() < 3) {
            throw new Exception("Nome do Autor deve ter pelo menos 3 caracteres.");
        }

        if (autor.getDataNascimento() == null) {
            throw new Exception("Data de Nascimento do Autor deve ser preenchida com uma data válida.");
        }

        if (autor.getBiografia() == null || autor.getBiografia().length() < 3) {
            throw new Exception("Biografia do Autor deve ter pelo menos 3 caracteres.");
        }

        if (autor.getLocalNascimento() == null || autor.getLocalNascimento().length() < 3) {
            throw new Exception("Local de Nascimento do Autor deve ter pelo menos 3 caracteres.");
        }

        if (autor.getWebsite() != null && !isValidURL(autor.getWebsite())) {
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
