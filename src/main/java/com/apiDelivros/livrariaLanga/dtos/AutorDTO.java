package com.apiDelivros.livrariaLanga.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.apiDelivros.livrariaLanga.models.Autor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutorDTO(
    UUID id,
    @NotBlank(message = "campo obrigatorio")
    String name,
    @NotBlank(message = "campo obrigatorio")
    String nacionalidade,
    @NotNull(message = "campo obrigatorio")
    LocalDate DataNascimento
) 
{
    public Autor mappearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.name);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(DataNascimento);
        return autor;
    }
}
