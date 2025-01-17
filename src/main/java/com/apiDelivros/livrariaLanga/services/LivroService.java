package com.apiDelivros.livrariaLanga.services;

import java.util.Optional;
import java.util.UUID;

import com.apiDelivros.livrariaLanga.models.Livro;
import com.apiDelivros.livrariaLanga.repositories.LivroRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LivroService {
    private LivroRepository livroRepository;
    public Livro salvar(Livro livro)
    {
        return livroRepository.save(livro);

    }
     public Optional<Livro> obterLivroPeloId(UUID id){
        return livroRepository.findById(id);
     }
     public void deletar(Livro livro){
        livroRepository.delete(livro);
     }
}
