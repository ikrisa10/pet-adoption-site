package com.launchacademy.petadoption.repositories;

import com.launchacademy.petadoption.models.PetSurrenderApplication;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetSurrenderApplicationRepository extends
    PagingAndSortingRepository<PetSurrenderApplication, Integer> {

}
