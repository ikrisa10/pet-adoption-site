package com.launchacademy.petadoption.controllers;

import com.launchacademy.petadoption.models.PetSurrenderFormValidator;
import com.launchacademy.petadoption.services.PetSurrenderApplicationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SurrenderApplicationRestControllerV1 {
  private PetSurrenderApplicationService petSurrenderApplicationService;

  @Autowired
  public SurrenderApplicationRestControllerV1(
      PetSurrenderApplicationService petSurrenderApplicationService) {
    this.petSurrenderApplicationService = petSurrenderApplicationService;
  }

  @PostMapping("/surrender")
  public ResponseEntity saveSurrenderApplication(@RequestBody @Valid
      PetSurrenderFormValidator petSurrenderFormValidator, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      for(int i = 0; i < fieldErrors.size(); i++) {
        errors.put(fieldErrors.get(i).getField(), fieldErrors.get(i).getDefaultMessage());
      }
      return new ResponseEntity<Map>(errors, HttpStatus.NOT_ACCEPTABLE);
    } else {
      return new ResponseEntity<>(petSurrenderApplicationService.savePetSurrenderApplication(petSurrenderFormValidator), HttpStatus.OK);
    }
  }
}
