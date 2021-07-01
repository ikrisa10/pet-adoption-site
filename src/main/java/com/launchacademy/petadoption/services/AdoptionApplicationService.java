package com.launchacademy.petadoption.services;

import com.launchacademy.petadoption.models.AdoptionApplication;
import com.launchacademy.petadoption.models.AdoptionApplicationFormValidator;
import com.launchacademy.petadoption.repositories.AdoptablePetRepository;
import com.launchacademy.petadoption.repositories.AdoptionApplicationRepository;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class AdoptionApplicationService {

  private AdoptionApplicationRepository adoptionApplicationRepository;
  private AdoptablePetRepository adoptablePetRepository;

  @Autowired
  public AdoptionApplicationService(AdoptionApplicationRepository adoptionApplicationRepository,
      AdoptablePetRepository adoptablePetRepository) {
    this.adoptionApplicationRepository = adoptionApplicationRepository;
    this.adoptablePetRepository = adoptablePetRepository;
  }

  public AdoptionApplication saveApplication(AdoptionApplicationFormValidator adoptionFormObj) {
    AdoptionApplication adoptionApplication = new AdoptionApplication();
    adoptionApplication.setName(adoptionFormObj.getName());
    adoptionApplication.setPhoneNumber(adoptionFormObj.getPhoneNumber());
    adoptionApplication.setEmail(adoptionFormObj.getEmail());
    adoptionApplication.setHomeStatus(adoptionFormObj.getHomeStatus());
    adoptionApplication.setAdoptablePet(
        adoptablePetRepository.findById(adoptionFormObj.getAdoptablePetId()).get());
    return this.adoptionApplicationRepository.save(adoptionApplication);
  }

  public List<AdoptionApplication> findPendingApplications() {
    return this.adoptionApplicationRepository.findPendingApplications();
  }

  public void delete(Integer applicationId) {
    AdoptionApplication adoptionApplication = this.adoptionApplicationRepository
        .findById(applicationId).get();
    this.adoptionApplicationRepository.delete(adoptionApplication);
  }

  public void editApplication(AdoptionApplicationFormValidator adoptionApplicationFormValidator,
      Integer applicationId) {
    this.adoptionApplicationRepository.editApplication(adoptionApplicationFormValidator.getName(),
        adoptionApplicationFormValidator.getPhoneNumber(),
        adoptionApplicationFormValidator.getEmail(),
        adoptionApplicationFormValidator.getHomeStatus(), applicationId);
  }

  public void adminUpdate(Map<String, String> adminFormReplies, Integer applicationId) {
    this.adoptionApplicationRepository.adminUpdate(adminFormReplies.get("applicationStatus"),
        adminFormReplies.get("adminComments"), applicationId);
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

  public AdoptionApplication findById(Integer applicationId) {
    return this.adoptionApplicationRepository.findById(applicationId)
        .orElseThrow(() -> new UrlNotFoundException());
  }
}
