package com.launchacademy.petadoption.seeders;

import com.launchacademy.petadoption.models.AdoptablePet;
import com.launchacademy.petadoption.models.PetType;
import com.launchacademy.petadoption.services.AdoptablePetService;
import com.launchacademy.petadoption.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartingSeeder implements CommandLineRunner {

  private PetTypeService petTypeService;
  private AdoptablePetService adoptablePetService;

  @Autowired
  public StartingSeeder(PetTypeService petTypeService, AdoptablePetService adoptablePetService) {
    this.petTypeService = petTypeService;
    this.adoptablePetService = adoptablePetService;
  }

  @Override
  public void run(String... args) throws Exception {

    PetType leeches = new PetType();
    leeches.setType("Leeches");
    leeches.setImgUrl("https://i.postimg.cc/8c3FJq1q/Leeches.jpg");
    leeches.setDescription("Your perfect friend for those detox cleanse types");

    PetType redGarras = new PetType();
    redGarras.setType("Red Garras");
    redGarras.setImgUrl("https://i.postimg.cc/jS65tW7V/Red-Garras.png");
    redGarras.setDescription(
        "The perfect pedicure pal, or doctor fish to cure your skin-borne diseases!");

    if (petTypeService.findAll().size() == 0) {
      petTypeService.save(leeches);
      petTypeService.save(redGarras);
    }

    AdoptablePet stan = new AdoptablePet();
    stan.setName("Stan");
    stan.setImgUrl("https://i.postimg.cc/pTzS8sT1/Stan-Leech.png");
    stan.setAge(3);
    stan.setVaccinationStatus(false);
    stan.setAdoptionStory(
        "Stan latched a ride home with a hiker while on holiday in the jungles of Costa Rica.  "
            + "He likes to go for long walks in stagnant pools.  His favorite food is O+.");
    stan.setPetType(leeches);

    AdoptablePet dora = new AdoptablePet();
    dora.setName("Dora");
    dora.setImgUrl("https://i.postimg.cc/zfbdTxvY/Vlad.png");
    dora.setAge(13);
    dora.setVaccinationStatus(true);
    dora.setAdoptionStory(
        "Dora grew up right here in town and comes to us as a rescue leech.  She was a key member of the medical team who performed reconstructive surgery on a high-profile client.");
    dora.setPetType(leeches);

    AdoptablePet larietta = new AdoptablePet();
    larietta.setName("Larietta");
    larietta.setImgUrl("https://i.postimg.cc/Wbyg0Tkt/Larietta.png");
    larietta.setAge(4);
    larietta.setVaccinationStatus(false);
    larietta.setAdoptionStory(
        "Larietta and her 288 siblings were born at a local beauty parlor where she spent the first half of her life dedicated to the beauty industry.  She specializes in corn removal and loves to listen to classic jazz.");
    larietta.setPetType(redGarras);

    AdoptablePet garry = new AdoptablePet();
    garry.setName("Garry");
    garry.setImgUrl("https://i.postimg.cc/9MBy99dj/Garry.png");
    garry.setAge(7);
    garry.setVaccinationStatus(false);
    garry.setAdoptionStory(
        "Garry has led a long life as a workhorse contributer as a traveling pedicurist on the fashion model circuit.  His delicate touch and precision is unparalled in the parasitic removal industry.  And the stories he could tell...");
    garry.setPetType(redGarras);

    if (adoptablePetService.findAll().size() == 0) {
      adoptablePetService.save(stan);
      adoptablePetService.save(dora);
      adoptablePetService.save(larietta);
      adoptablePetService.save(garry);
    }
  }
}
