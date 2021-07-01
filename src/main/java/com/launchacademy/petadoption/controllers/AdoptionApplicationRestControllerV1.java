package com.launchacademy.petadoption.controllers;

import com.launchacademy.petadoption.models.AdoptionApplication;
import com.launchacademy.petadoption.models.AdoptionApplicationFormValidator;
import com.launchacademy.petadoption.services.AdoptablePetService;
import com.launchacademy.petadoption.services.AdoptionApplicationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AdoptionApplicationRestControllerV1 {
  private AdoptionApplicationService adoptionApplicationService;
  private AdoptablePetService adoptablePetService;

  @Autowired
  public AdoptionApplicationRestControllerV1(
      AdoptionApplicationService adoptionApplicationService, AdoptablePetService adoptablePetService) {
    this.adoptionApplicationService = adoptionApplicationService;
    this.adoptablePetService = adoptablePetService;
  }

  @PostMapping("/application")
  public ResponseEntity saveAdoptionApplication(@RequestBody @Valid
      AdoptionApplicationFormValidator adoptionApplicationFormValidator, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      for(int i = 0; i < fieldErrors.size(); i++) {
          errors.put(fieldErrors.get(i).getField(), fieldErrors.get(i).getDefaultMessage());
      }
      return new ResponseEntity<Map>(errors, HttpStatus.NOT_ACCEPTABLE);
    }
    return new ResponseEntity<>(adoptionApplicationService.saveApplication(adoptionApplicationFormValidator), HttpStatus.OK);
  }

  @GetMapping("/pending_applications")
  public List<AdoptionApplication> getPendingApplications() {
    return adoptionApplicationService.findPendingApplications();
  }

  @DeleteMapping("/delete/{applicationId}")
  public ResponseEntity deleteApplication (@PathVariable Integer applicationId) {
    adoptionApplicationService.delete(applicationId);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/application/{applicationId}")
  public Map<String, String> getApplicationById(@PathVariable Integer applicationId) {
    AdoptionApplication applicationDataToEdit = adoptionApplicationService.findById(applicationId);
    Map<String, String> dataToSend = new HashMap<>();
    dataToSend.put("name", applicationDataToEdit.getName());
    dataToSend.put("phoneNumber", applicationDataToEdit.getPhoneNumber());
    dataToSend.put("email", applicationDataToEdit.getEmail());
    dataToSend.put("homeStatus", applicationDataToEdit.getHomeStatus());
    return dataToSend;
  }

  @PostMapping("/application/edit/{applicationId}")
  public ResponseEntity editAdoptionApplication(@RequestBody @Valid
      AdoptionApplicationFormValidator adoptionApplicationFormValidator, BindingResult bindingResult, @PathVariable Integer applicationId) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      for(int i = 0; i < fieldErrors.size(); i++) {
        errors.put(fieldErrors.get(i).getField(), fieldErrors.get(i).getDefaultMessage());
      }
      return new ResponseEntity<Map>(errors, HttpStatus.NOT_ACCEPTABLE);
    }
    adoptionApplicationService.editApplication(adoptionApplicationFormValidator, applicationId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/application/update/{petId}/{applicationId}")
  public ResponseEntity adminUpdate(@RequestBody Map<String, String> adminFormReplies, @PathVariable Integer petId, @PathVariable Integer applicationId) {
    try {
      adoptionApplicationService.adminUpdate(adminFormReplies, applicationId);
      adoptablePetService.adminUpdate(adminFormReplies.get("applicationStatus"), petId);
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }
  }
}
