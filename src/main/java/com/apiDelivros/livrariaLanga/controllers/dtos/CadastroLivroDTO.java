package com.apiDelivros.livrariaLanga.controllers.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.apiDelivros.livrariaLanga.enums.GeneroLivro;
import com.apiDelivros.livrariaLanga.models.Autor;
import com.apiDelivros.livrariaLanga.models.Livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatorio")
        String isbn,
        @NotBlank(message = "campo obrigatorio")
        String titulo,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "nao pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID idAutor
        ) { 
                public Livro mappearLivro(){

                Livro livro = new Livro();
                livro.setTitulo(titulo);
                livro.setIsbn(isbn);
                livro.setDataPublicacao(dataPublicacao);
                livro.setPreco(preco);
                livro.setAutor(idAutor);
                return livro;
                }      
        
    
}
