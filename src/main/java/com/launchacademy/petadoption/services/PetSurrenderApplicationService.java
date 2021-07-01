package com.launchacademy.petadoption.services;

import com.launchacademy.petadoption.models.PetSurrenderApplication;
import com.launchacademy.petadoption.models.PetSurrenderFormValidator;
import com.launchacademy.petadoption.models.PetType;
import com.launchacademy.petadoption.repositories.PetSurrenderApplicationRepository;
import com.launchacademy.petadoption.repositories.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetSurrenderApplicationService {
  private PetSurrenderApplicationRepository petSurrenderApplicationRepository;
  private PetTypeRepository petTypeRepository;

  @Autowired
  public PetSurrenderApplicationService(
      PetSurrenderApplicationRepository petSurrenderApplicationRepository, PetTypeRepository petTypeRepository) {
    this.petSurrenderApplicationRepository = petSurrenderApplicationRepository;
    this.petTypeRepository = petTypeRepository;
  }

  public PetSurrenderApplication savePetSurrenderApplication (PetSurrenderFormValidator petSurrenderFormValidator) {
    PetSurrenderApplication petSurrenderApplication = new PetSurrenderApplication();
    petSurrenderApplication.setName(petSurrenderFormValidator.getName());
    petSurrenderApplication.setPhoneNumber(petSurrenderFormValidator.getPhoneNumber());
    petSurrenderApplication.setEmail(petSurrenderFormValidator.getEmail());
    petSurrenderApplication.setPetName(petSurrenderFormValidator.getPetName());
    if(petSurrenderFormValidator.getPetAge() != null) {
      petSurrenderApplication.setPetAge(petSurrenderFormValidator.getPetAge());
    }
    petSurrenderApplication.setPetImageUrl(petSurrenderFormValidator.getPetImageUrl());
    petSurrenderApplication.setVaccinationStatus(petSurrenderFormValidator.getVaccinationStatus());
    PetType petType = this.petTypeRepository.findByTypeIgnoreCase(petSurrenderFormValidator.getPetType()).orElseThrow();
    petSurrenderApplication.setPetType(petType);
    return this.petSurrenderApplicationRepository.save(petSurrenderApplication);
  }
}
