package com.apiDelivros.livrariaLanga.models;

import java.time.LocalDate;
import java.util.UUID;

import com.apiDelivros.livrariaLanga.enums.GeneroLivro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Livro {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String isbn;
    @Column
    private Double price;
    @Column(nullable = false)
    private LocalDate dataPublicacao;
    @Column
    private GeneroLivro genero;
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
    
}
