package com.launchacademy.petadoption.controllers;

import com.launchacademy.petadoption.models.AdoptablePet;
import com.launchacademy.petadoption.models.PetType;
import com.launchacademy.petadoption.services.AdoptablePetService;
import com.launchacademy.petadoption.services.AdoptionApplicationService;
import com.launchacademy.petadoption.services.PetSurrenderApplicationService;
import com.launchacademy.petadoption.services.PetTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pets")
public class PetsRestControllerV1 {

  private PetTypeService petTypeService;
  private AdoptablePetService adoptablePetService;

  @Autowired
  public PetsRestControllerV1(PetTypeService petTypeService,
      AdoptablePetService adoptablePetService,
      AdoptionApplicationService adoptionApplicationService,
      PetSurrenderApplicationService petSurrenderApplicationService) {
    this.petTypeService = petTypeService;
    this.adoptablePetService = adoptablePetService;
  }

  @GetMapping
  public List<PetType> getPetTypes() {
    return petTypeService.findAll();
  }

  @GetMapping("/{type}")
  public List<AdoptablePet> getPetsOfSpecificType(@PathVariable String type) {
    return adoptablePetService.findAvailablePetsByType(type);
  }

  @GetMapping("/{type}/{animalId}")
  public AdoptablePet getAdoptablePet(@PathVariable Integer animalId) {
    return adoptablePetService.findById(animalId);
  }

  @GetMapping("/adopted")
  public List<AdoptablePet> getAdoptedPets() {
    return adoptablePetService.findHappyAdoptedPets();
  }
}
