package com.launchacademy.petadoption.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class AdoptionApplicationFormValidator {

  @NotBlank(message = "Name field must be filled out and can not be blank")
  @Length(max = 255, message = "Name entry should be 255 characters max")
  private String name;

  @NotBlank(message = "Phone number field must be filled out and can not be blank")
  @Pattern(regexp = "(\\d+)", message = "Phone number should only contain digits")
  @Length(max = 15, message = "Phone number entry should be 15 characters max")
  private String phoneNumber;

  @NotBlank(message = "Email field must be filled out, and can not be blank")
  @Email(message = "Email should be provided with the valid email format (i.e. @gmail.com, @aol.com)")
  @Length(max = 320, message = "Email entry should be 320 characters max")
  private String email;

  @NotBlank(message = "Ensure to select if you own or rent your home")
  @Length(max = 255)
  private String homeStatus;

  @NotNull
  private Integer adoptablePetId;

}
