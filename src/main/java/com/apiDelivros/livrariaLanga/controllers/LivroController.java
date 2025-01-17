package com.apiDelivros.livrariaLanga.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiDelivros.livrariaLanga.controllers.dtos.CadastroLivroDTO;
import com.apiDelivros.livrariaLanga.controllers.dtos.ErroResposta;
import com.apiDelivros.livrariaLanga.controllers.dtos.ResultadoPesquisaLivroDTO;
import com.apiDelivros.livrariaLanga.controllers.mappers.LivroMapper;
import com.apiDelivros.livrariaLanga.models.Livro;
import com.apiDelivros.livrariaLanga.services.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("livros")
public class LivroController implements GenericController{
   private LivroService livroService;
    @Autowired
   private LivroMapper livroMapper;
   @PostMapping
   public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
    try {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.accepted().location(location).build();
        
    } catch (Exception e) {
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }
   }

   @GetMapping("{id}")
public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
    return livroService.obterLivroPeloId(UUID.fromString(id)).map(livro -> {
        var dto = livroMapper.toDTO(livro);
        return ResponseEntity.ok(dto);
    }).orElseGet(() -> ResponseEntity.notFound().build());
}
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return livroService.obterLivroPeloId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
