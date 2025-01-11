package com.apiDelivros.livrariaLanga.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErroResposta(
    int status, String messagem, List<ErroCampo> erros
) 
{
    public static ErroResposta respostaPadrao(String mensagem){
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of()); 
    }

    public static ErroResposta conflito(String messagem){
        return new ErroResposta(HttpStatus.CONFLICT.value(), messagem,List.of());
    }
    
}
