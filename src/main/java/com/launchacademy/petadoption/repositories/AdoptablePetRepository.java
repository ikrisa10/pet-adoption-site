package com.launchacademy.petadoption.repositories;

import com.launchacademy.petadoption.models.AdoptablePet;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdoptablePetRepository extends PagingAndSortingRepository<AdoptablePet, Integer> {

  @Query("SELECT p FROM AdoptablePet p WHERE type_id = :petTypeId")
  public List<AdoptablePet> findAllByPetType(@Param("petTypeId") Integer petTypeId);

  @Modifying
  @Transactional
  @Query("update AdoptablePet p set adoption_status = :applicationStatus where id = :petId")
  public void adminUpdate(@Param("applicationStatus") String applicationStatus,
      @Param("petId") Integer petId);
}
