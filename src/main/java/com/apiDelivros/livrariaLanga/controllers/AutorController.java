package com.apiDelivros.livrariaLanga.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apiDelivros.livrariaLanga.controllers.dtos.AutorDTO;
import com.apiDelivros.livrariaLanga.controllers.dtos.ErroResposta;
import com.apiDelivros.livrariaLanga.exceptions.OperacaoNaoPermitidaException;
import com.apiDelivros.livrariaLanga.exceptions.RegistroDuplicadoException;
import com.apiDelivros.livrariaLanga.models.Autor;
import com.apiDelivros.livrariaLanga.services.AutorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("autor")
public class AutorController {
    @Autowired
    private AutorService autorService;

    @PostMapping
public ResponseEntity<Object> salvarAutor(@RequestBody @Valid AutorDTO autorDTO) {
    try {
        // Map the DTO to the entity and save the author
        Autor autor = autorDTO.mappearAutor();
        Autor savedAutor = autorService.salvarAutor(autor);

        // If you want to return the URI of the newly created resource:
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedAutor.getId())
                        .toUri();

        // Return HTTP 201 Created with the location header
        return ResponseEntity.created(location).body(savedAutor);

    } catch (RegistroDuplicadoException e) {
        // Handle the exception when a duplicate record is found
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    } catch (Exception e) {
        // Handle any other unexpected errors
        var erroDTO = ErroResposta.respostaPadrao("Erro inesperado: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroDTO);
    }
}



    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterAutorPeloId(@PathVariable("id") String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> auOptional = autorService.obterAutorPeloId(idAutor);

            if (auOptional.isPresent()) {
                Autor autor = auOptional.get();
                AutorDTO autorDTO = new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getNacionalidade(),
                        autor.getDataNascimento());
                return ResponseEntity.ok(autorDTO); // Retorna a resposta com status 200 e o DTO
            }

            return ResponseEntity.notFound().build(); // Retorna status 404 caso o autor não seja encontrado
        } catch (IllegalArgumentException e) {
            // Trata o caso de UUID inválido
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        try {
            // Convert the string ID to a UUID
            var idAutor = UUID.fromString(id); // This could throw IllegalArgumentException if the UUID is invalid
            Optional<Autor> auOptional = autorService.obterAutorPeloId(idAutor);
    
            // If the author does not exist, return a 404 Not Found response
            if (auOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
    
            // Proceed to delete the author if found
            autorService.deletarAutor(auOptional.get());
            return ResponseEntity.noContent().build(); // No content returned on success (204 status)
            
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format (bad request)
            var resposta = ErroResposta.respostaPadrao("ID inválido. Formato de UUID incorreto.");
            return ResponseEntity.badRequest().body(resposta);
            
        } catch (OperacaoNaoPermitidaException e) {
            // Handle your custom exception (e.g., operation not allowed)
            var resposta = ErroResposta.respostaPadrao(e.getLocalizedMessage());
            return ResponseEntity.status(resposta.status()).body(resposta);
            
        } catch (Exception e) {
            // General catch-all for other exceptions (e.g., database issues)
            var resposta = ErroResposta.respostaPadrao("Erro inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
        }
    }
    
        
    

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
       @RequestParam(value = "nome", required = false) String nome,
       @RequestParam(value = "nacionalidade", required = false)  String nacionalidade){
        List<Autor> resultado = autorService.pesquisarByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(autor-> new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento())).collect(Collectors.toList());

        return ResponseEntity.ok(lista);

    }


    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
       @PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO){
        try{
            var idAutor = UUID.fromString(id);
        Optional<Autor> auOptional = autorService.obterAutorPeloId(idAutor);

        if(auOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = auOptional.get();
        autor.setNome(autorDTO.name());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.DataNascimento());
        autorService.actualizar(autor);
        return ResponseEntity.noContent().build();
    }catch(RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);

    }


        }
        
}
