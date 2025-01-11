package com.apiDelivros.livrariaLanga.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.apiDelivros.livrariaLanga.exceptions.RegistroDuplicadoException;
import com.apiDelivros.livrariaLanga.models.Autor;
import com.apiDelivros.livrariaLanga.repositories.AutorRepository;
@Component
public class AutorValidator {
    private AutorRepository autorRepository;
    public AutorValidator(AutorRepository autorRepository){
        this.autorRepository=autorRepository;
    }
    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Ja existe esse autor Cadastrado");
        }
        
    }
    private boolean existeAutorCadastrado(Autor autor){
      Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),autor.getDataNascimento() , autor.getNacionalidade());
      if(autor.getId()==null){
        return autorEncontrado.isPresent();
      }
      return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
    
    

}
