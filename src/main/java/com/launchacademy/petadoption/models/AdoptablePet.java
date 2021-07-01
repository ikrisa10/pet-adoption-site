package com.launchacademy.petadoption.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adoptable_pets")
public class AdoptablePet {

  @Id
  @SequenceGenerator(name = "adoptable_pets_generator", sequenceName = "adoptable_pets_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adoptable_pets_generator")
  @Column(name = "id")
  private Integer id;

  @NotBlank
  @Length(max = 255)
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank
  @URL
  @Length(max = 500)
  @Column(name = "img_url", nullable = false)
  private String imgUrl;

  @Min(value = 0)
  @Column(name = "age")
  private Integer age;

  @Column(name = "vaccination_status")
  private Boolean vaccinationStatus = false;

  @NotBlank
  @Column(name = "adoption_story", nullable = false)
  private String adoptionStory;

  @NotBlank
  @Length(max = 255)
  @Column(name = "adoption_status", nullable = false)
  private String adoptionStatus = "denied";

  @NotNull
  @ManyToOne
  @JoinColumn(name = "type_id")
  @JsonIgnoreProperties("adoptablePets")
  private PetType petType;

  @OneToMany(mappedBy = "adoptablePet", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("adoptablePet")
  private List<AdoptionApplication> adoptionApplications;
}
