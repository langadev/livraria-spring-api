package com.apiDelivros.livrariaLanga.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.apiDelivros.livrariaLanga.exceptions.OperacaoNaoPermitidaException;
import com.apiDelivros.livrariaLanga.models.Autor;
import com.apiDelivros.livrariaLanga.repositories.AutorRepository;
import com.apiDelivros.livrariaLanga.repositories.LivroRepository;
import com.apiDelivros.livrariaLanga.validator.AutorValidator;

@Service
public class AutorService {
    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;


    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator, LivroRepository livroRepository){
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
    }
    public Autor salvarAutor(Autor autor){
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterAutorPeloId(UUID id){
        return autorRepository.findById(id);
    }
    public void deletarAutor(Autor autor){
        if(pussuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Nao e permitido excluir autor Que O Possui Livros Cadastrados");
        }
        autorRepository.delete(autor);
    }
    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
    
        if (nome != null) {
            return autorRepository.findByNome(nome);
        }
    
        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }
    
        return autorRepository.findAll(); // Caso nenhum filtro seja aplicado, retorna todos
    }


    public List<Autor> pesquisarByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor,matcher);
        return autorRepository.findAll(autorExample);
    }


    public void actualizar(Autor autor){

        if(autor.getId()==null){
            throw new IllegalArgumentException("Nao existe esse autor");
        }
        autorValidator.validar(autor);
  autorRepository.save(autor);
}

public boolean pussuiLivro(Autor autor){
    return livroRepository.existsByAutor(autor);
}
    
}
