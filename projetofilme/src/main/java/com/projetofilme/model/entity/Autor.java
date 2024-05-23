package com.projetofilme.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(nullable = false, length = 1000)
    private String biografia;

    public String getLocalNascimento() {
        return localNascimento;
    }

    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    @Column(nullable = false)
    private String localNascimento;

    private String website;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonManagedReference // Adicionando essa anotação
    private List<Filme> filmes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }
}