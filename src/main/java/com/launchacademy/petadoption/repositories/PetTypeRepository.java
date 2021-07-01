package com.launchacademy.petadoption.repositories;

import com.launchacademy.petadoption.models.PetType;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends PagingAndSortingRepository<PetType, Integer> {
  public Optional<PetType> findByTypeIgnoreCase(String type);
}
