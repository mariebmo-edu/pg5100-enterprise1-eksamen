package no.kristiania.eksamen.service

import no.kristiania.eksamen.dto.AnimalDto
import no.kristiania.eksamen.dto.AnimalDtoForUpdate
import no.kristiania.eksamen.model.AnimalEntity
import no.kristiania.eksamen.repo.AnimalRepo
import no.kristiania.eksamen.repo.StatusRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalService(@Autowired private val animalRepo: AnimalRepo, @Autowired private val statusRepo: StatusRepo) {

    fun getAnimals(): List<AnimalEntity> {
        return animalRepo.findAll()
    }

    fun getAnimalsByName(name: String): List<AnimalEntity>? {
        return animalRepo.findByNameIgnoreCase(name)
    }

    fun getAnimalsByStatus(statusId: Long): List<AnimalEntity>? {
        return animalRepo.findByStatusId(statusId)
    }

    fun getAnimalsByBreed(breed: String): List<AnimalEntity>? {
        return animalRepo.findByBreedIgnoreCase(breed)
    }

    fun getAnimalById(id: Long): AnimalEntity? {
        return animalRepo.getById(id)
    }

    fun registerAnimal(animalDto: AnimalDto): AnimalEntity? {
        return try {
            val newAnimal = AnimalEntity(
                name = animalDto.name,
                breed = animalDto.breed,
                age = animalDto.age,
                health = animalDto.health,
                status = statusRepo.getById(animalDto.statusId)
            )

            animalRepo.save(newAnimal)
        } catch (error : Error){
            null
        }
    }

    fun updateAnimal(id: Long, animalDto: AnimalDtoForUpdate): AnimalEntity? {
        if (animalRepo.existsById(id)) {
            val currentAnimal = animalRepo.getById(id)
            return try{
                val updatedAnimal = AnimalEntity(
                    id = currentAnimal.id,
                    name = animalDto.name ?: currentAnimal.name,
                    breed = animalDto.breed ?: currentAnimal.breed,
                    age = animalDto.age ?: currentAnimal.age,
                    health = animalDto.health ?: currentAnimal.health,
                    created = currentAnimal.created,
                    status = animalDto.statusId?.let { statusRepo.getById(it) } ?: currentAnimal.status
                )
                animalRepo.save(updatedAnimal)
            } catch (error : Error){
                null
            }
        }
        return null
    }

    fun updateAnimalStatus(id: Long, statusId: Long): Boolean {
        statusRepo.existsById(statusId).let {
            animalRepo.existsById(id).let {
                val currentAnimal = animalRepo.getById(id)

                val newAnimal = AnimalEntity(
                    id = currentAnimal.id,
                    breed = currentAnimal.breed,
                    age = currentAnimal.age,
                    health = currentAnimal.health,
                    created = currentAnimal.created,
                    status = statusRepo.getById(statusId)
                )

                animalRepo.save(newAnimal)
                return true
            }
        }
        return false
    }

    fun deleteAnimal(id: Long): Boolean {
        if (animalRepo.existsById(id)) {
            animalRepo.deleteById(id)
            return true
        }
        return false
    }
}