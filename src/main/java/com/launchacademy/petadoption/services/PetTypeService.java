package com.launchacademy.petadoption.services;

import com.launchacademy.petadoption.models.PetType;
import com.launchacademy.petadoption.repositories.PetTypeRepository;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class PetTypeService {

  private PetTypeRepository petTypeRepository;

  @Autowired
  public PetTypeService(PetTypeRepository petTypeRepository) {
    this.petTypeRepository = petTypeRepository;
  }

  public List<PetType> findAll() {
    return (List<PetType>) this.petTypeRepository.findAll();
  }

  public void save(PetType petType) {
    this.petTypeRepository.save(petType);
  }

  @NoArgsConstructor
  private class UrlNotFoundException extends RuntimeException {

  }

  @ControllerAdvice
  private class UrlNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String urlNotFoundHandler(UrlNotFoundException ex) {
      return ex.getMessage();
    }
  }

  public PetType findByType(String type) {
    return this.petTypeRepository.findByTypeIgnoreCase(type)
        .orElseThrow(() -> new UrlNotFoundException());
  }
}
