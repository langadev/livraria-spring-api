package com.apiDelivros.livrariaLanga.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiDelivros.livrariaLanga.models.Autor;
import com.apiDelivros.livrariaLanga.models.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID>{
    List<Livro> findByAutor(Autor autor);
    boolean existsByAutor(Autor autor);
}
