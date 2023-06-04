package com.example.lovelypet.service;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

   private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

//    Optional<Pet> findByPetTypeId(PetType petType){
//        return repository.findByPetTypeId(petType);
//    }

    List<Pet> findByUserId(User user){
        return repository.findByUserId(user);
    }

    public Pet create(User user,String petName,String petPhoto){

        Pet entity = new Pet();
        entity.setUserId(user);
        entity.setPetName(petName);
        entity.setPetPhoto(petPhoto);
        return repository.save(entity);
    }

}
