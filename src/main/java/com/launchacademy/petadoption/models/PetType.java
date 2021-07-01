package com.launchacademy.petadoption.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pet_types")
public class PetType {

  @Id
  @SequenceGenerator(name = "pet_types_generator", sequenceName = "pet_types_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_types_generator")
  @Column(name = "id")
  private Integer id;

  @NotBlank(message = "Type field should be provided")
  @Length(max = 250, message = "Maximum length for type field is 250 characters")
  @Column(name = "type", nullable = false)
  private String type;

  @NotBlank(message = "Image URL should be provided")
  @URL(message = "Should be a valid url link")
  @Length(max = 500, message = "Maximum length for url field is 500 characters")
  @Column(name = "img_url", nullable = false)
  private String imgUrl;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "petType", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("petType")
  private List<AdoptablePet> adoptablePets;

  @OneToMany(mappedBy = "petType", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("petType")
  private List<PetSurrenderApplication> petSurrenderApplications;
}
