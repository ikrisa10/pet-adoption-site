package com.launchacademy.petadoption.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adoption_applications")
public class AdoptionApplication {

  @Id
  @SequenceGenerator(name = "adoption_applications_generator", sequenceName = "adoption_applications_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adoption_applications_generator")
  @Column(name = "id")
  private Integer id;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @NotBlank
  @Email
  @Column(name = "email", nullable = false)
  private String email;

  @NotBlank
  @Column(name = "home_status", nullable = false)
  private String homeStatus;

  @NotBlank
  @Column(name = "application_status", nullable = false)
  private String applicationStatus = "pending";

  @Column(name = "admin_comments")
  private String adminComments;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "adoptable_pet_id")
  @JsonIgnoreProperties("adoptionApplications")
  private AdoptablePet adoptablePet;
}
