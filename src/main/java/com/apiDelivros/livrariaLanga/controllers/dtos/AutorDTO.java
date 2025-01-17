package com.apiDelivros.livrariaLanga.controllers.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.apiDelivros.livrariaLanga.models.Autor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AutorDTO(
    UUID id,
    @NotBlank(message = "campo obrigatorio")
    @Size(min = 2, max = 100, message = "fora do tamanho padrao")
    String name,
    @NotBlank(message = "campo obrigatorio")
    @Size(min = 2, max = 50, message = "fora do tamanho padrao")
    String nacionalidade,
    @NotNull(message = "campo obrigatorio")
    @Past(message="nao pode ser uma data futura")
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
