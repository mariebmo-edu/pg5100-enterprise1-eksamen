package no.kristiania.eksamen.service

import no.kristiania.eksamen.controller.NewAnimalInfo
import no.kristiania.eksamen.model.AnimalEntity
import no.kristiania.eksamen.repo.AnimalRepo
import no.kristiania.eksamen.repo.StatusRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalService (@Autowired private val animalRepo: AnimalRepo, @Autowired private val statusRepo: StatusRepo) {

    fun getAnimals(): List<AnimalEntity> {
        return animalRepo.findAll()
    }

    fun getAnimalsByName(name: String): List<AnimalEntity> {
        return animalRepo.findByName(name)
    }

    fun getAnimalsByStatus(statusId: Long) : List<AnimalEntity>{
        return animalRepo.findByStatusId(statusId)
    }

    fun getAnimalsByBreed(breed: String) : List<AnimalEntity>{
        return animalRepo.findByBreed(breed)
    }

    fun getAnimalById(id: Long) : AnimalEntity {
        return animalRepo.getById(id)
    }

    fun registerAnimal(newAnimalInfo: NewAnimalInfo) : AnimalEntity{
        val newAnimal = AnimalEntity(name = newAnimalInfo.name, breed = newAnimalInfo.breed, age = newAnimalInfo.age, health = newAnimalInfo.health, status = statusRepo.getById(newAnimalInfo.statusId))

        return animalRepo.save(newAnimal)
    }
}