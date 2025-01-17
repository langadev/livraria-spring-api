package com.apiDelivros.livrariaLanga.controllers.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apiDelivros.livrariaLanga.controllers.dtos.ErroCampo;
import com.apiDelivros.livrariaLanga.controllers.dtos.ErroResposta;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResposta handleMethoargumentNotValidException(MethodArgumentNotValidException e){
       List<FieldError> fieldErrors = e.getFieldErrors();
     List<ErroCampo> listaDeErros=  fieldErrors.stream().
     map(fe-> new ErroCampo(fe.getField(),fe.getDefaultMessage())).collect(Collectors.toList());
    

         return new ErroResposta(
             HttpStatus.UNPROCESSABLE_ENTITY.value(),
             "Erro de validação.",
             listaDeErros);
};

}
