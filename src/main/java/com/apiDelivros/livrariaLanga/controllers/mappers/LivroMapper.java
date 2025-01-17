package com.apiDelivros.livrariaLanga.controllers.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.apiDelivros.livrariaLanga.controllers.dtos.CadastroLivroDTO;
import com.apiDelivros.livrariaLanga.controllers.dtos.ResultadoPesquisaLivroDTO;
import com.apiDelivros.livrariaLanga.models.Livro;
import com.apiDelivros.livrariaLanga.repositories.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
    
}
