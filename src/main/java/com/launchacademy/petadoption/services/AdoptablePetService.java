package com.launchacademy.petadoption.services;

import com.launchacademy.petadoption.models.AdoptablePet;
import com.launchacademy.petadoption.models.PetType;
import com.launchacademy.petadoption.repositories.AdoptablePetRepository;
import com.launchacademy.petadoption.repositories.PetTypeRepository;
import java.util.ArrayList;
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
public class AdoptablePetService {

  private AdoptablePetRepository adoptablePetRepository;
  private PetTypeRepository petTypeRepository;

  @Autowired
  public AdoptablePetService(AdoptablePetRepository adoptablePetRepository,
      PetTypeRepository petTypeRepository) {
    this.adoptablePetRepository = adoptablePetRepository;
    this.petTypeRepository = petTypeRepository;
  }

  public List<AdoptablePet> findAll() {
    return (List<AdoptablePet>) this.adoptablePetRepository.findAll();
  }

  public void save(AdoptablePet adoptablePet) {
    this.adoptablePetRepository.save(adoptablePet);
  }

  public List<AdoptablePet> findByPetType(String type) {
    PetType petType = this.petTypeRepository.findByTypeIgnoreCase(type)
        .orElseThrow(() -> new UrlNotFoundException());
    return this.adoptablePetRepository.findAllByPetType(petType.getId());
  }

  public List<AdoptablePet> findAvailablePetsByType(String type) {
    List<AdoptablePet> availablePets = new ArrayList<>();
    List<AdoptablePet> allPets = this.findByPetType(type);
    for (AdoptablePet adoptablePet : allPets) {
      if (adoptablePet.getAdoptionStatus().equals("denied")) {
        availablePets.add(adoptablePet);
      }
    }
    return availablePets;
  }

  public List<AdoptablePet> findHappyAdoptedPets() {
    List<AdoptablePet> allPets = this.findAll();
    List<AdoptablePet> happyAdoptedPets = new ArrayList<>();
    for (AdoptablePet adoptablePet : allPets) {
      if (adoptablePet.getAdoptionStatus().equals("approved")) {
        happyAdoptedPets.add(adoptablePet);
      }
    }
    return happyAdoptedPets;
  }

  public void adminUpdate(String applicationStatus, Integer petId) {
    this.adoptablePetRepository.adminUpdate(applicationStatus, petId);
  }

  @NoArgsConstructor
  private class UrlNotFoundException extends RuntimeException {

  }

  ;

  @ControllerAdvice
  private class UrlNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String urlNotFoundHandler(UrlNotFoundException ex) {
      return ex.getMessage();
    }
  }

  public AdoptablePet findById(Integer animalId) {
    return this.adoptablePetRepository.findById(animalId)
        .orElseThrow(() -> new UrlNotFoundException());
  }
}
