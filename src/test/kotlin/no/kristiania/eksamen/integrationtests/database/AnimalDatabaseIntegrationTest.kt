package no.kristiania.eksamen.integrationtests.database

import no.kristiania.eksamen.service.AnimalService
import no.kristiania.eksamen.testdata.DummyData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(AnimalService::class)
class AnimalDatabaseIntegrationTest(@Autowired private val animalService : AnimalService) {

    @Test
    fun shouldGetAnimals(){
        val result = animalService.getAnimals()
        assert(result.size == 3)
    }

    @Test
    fun shouldRegisterAndFindUser(){

        val newAnimalInfo = DummyData().getTestAnimalDtoList()[0]
        val createdAnimal = animalService.registerAnimal(newAnimalInfo)

        assert(createdAnimal?.name == newAnimalInfo.name)

        val foundAnimal = newAnimalInfo.name?.let { animalService.getAnimalsByName(it)?.get(0) }

        assert(foundAnimal?.name == createdAnimal?.name)
    }

    @Test
    fun shouldDeleteUser(){

        assert(animalService.deleteAnimal(3))

    }

    @Test
    fun shouldUpdateAnimal(){
        val animalUpdate = DummyData().getTestAnimalDtoForUpdateList()[0]

        animalService.updateAnimal(1, animalUpdate)

        val animal = animalService.getAnimalById(1)


        assert(animal?.name == animalUpdate.name)
        assert(animal?.breed != animalUpdate.breed)
    }
}