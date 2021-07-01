package com.launchacademy.petadoption.repositories;

import com.launchacademy.petadoption.models.AdoptionApplication;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdoptionApplicationRepository extends
    PagingAndSortingRepository<AdoptionApplication, Integer> {

  @Query("select a from AdoptionApplication a where application_status = 'pending'")
  public List<AdoptionApplication> findPendingApplications();

  @Modifying
  @Transactional
  @Query("update AdoptionApplication a set name = :name, phone_number = :phoneNumber, email = :email, home_status = :homeStatus where id = :applicationId")
  public void editApplication(@Param("name") String name, @Param("phoneNumber") String phoneNumber,
      @Param("email") String email, @Param("homeStatus") String homeStatus,
      @Param("applicationId") Integer applicationId);

  @Modifying
  @Transactional
  @Query("update AdoptionApplication a set application_status = :applicationStatus, admin_comments = :adminComments where id = :applicationId")
  public void adminUpdate(@Param("applicationStatus") String applicationStatus,
      @Param("adminComments") String adminComments, @Param("applicationId") Integer applicationId);
}
