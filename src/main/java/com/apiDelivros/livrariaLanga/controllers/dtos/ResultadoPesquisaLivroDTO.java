package com.apiDelivros.livrariaLanga.controllers.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.apiDelivros.livrariaLanga.enums.GeneroLivro;

public record ResultadoPesquisaLivroDTO(
    UUID id,
     String isbn,
    String titulo,
    LocalDate dataPublicacao,
    GeneroLivro genero,
    BigDecimal preco,
    AutorDTO autor
   
) {
    
}
