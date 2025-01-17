package com.apiDelivros.livrariaLanga.controllers.mappers;

import org.mapstruct.Mapper;

import com.apiDelivros.livrariaLanga.controllers.dtos.AutorDTO;
import com.apiDelivros.livrariaLanga.models.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(Autor autor) ;
    AutorDTO toDTO(Autor autor) ;

    
}
