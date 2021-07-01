package com.launchacademy.petadoption.models;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Getter
@Setter
public class PetSurrenderFormValidator {
  @NotBlank(message = "Name field must be filled out and can not be blank")
  @Length(max = 255)
  private String name;

  @NotBlank(message = "Phone number field must be filled out and can not be blank")
  @Pattern(regexp = "(\\d+)", message = "Phone number should only contain digits")
  @Length(max = 15, message = "Phone number entry should be 15 characters max")
  private String phoneNumber;

  @NotBlank (message = "Email field must be filled out, and can not be blank")
  @Email(message = "Email should be provided with the valid email format (i.e. @gmail.com, @aol.com)")
  @Length(max = 320, message = "Email entry should be 320 characters max")
  private String email;

  @NotBlank(message = "Pet name field must be filled out and can not be blank")
  @Length(max = 255)
  private String petName;

  @Min(value = 1, message = "Pet age field should only contain positive digits or be empty if unknown")
  private Integer petAge;

  @NotBlank(message = "Url field must be filled out and can not be blank")
  @URL
  @Length(max = 500)
  private String petImageUrl;

  @Column(name = "vaccination_status")
  private Boolean vaccinationStatus;

  @NotBlank
  @Length(max = 255)
  private String applicationStatus = "pending";

  @NotBlank (message = "Ensure to select the pet type from provided list")
  private String petType;
}
