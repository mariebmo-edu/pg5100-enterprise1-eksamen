package no.kristiania.eksamen.unittests.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.eksamen.dto.AnimalDto
import no.kristiania.eksamen.model.AnimalEntity
import no.kristiania.eksamen.model.StatusEntity
import no.kristiania.eksamen.repo.AnimalRepo
import no.kristiania.eksamen.repo.StatusRepo
import no.kristiania.eksamen.service.AnimalService
import no.kristiania.eksamen.testdata.DummyData
import org.junit.jupiter.api.Test

class AnimalServiceUnitTest {

    private val animalRepo = mockk<AnimalRepo>()
    private val statusRepo = mockk<StatusRepo>()
    private val animalService = AnimalService(animalRepo, statusRepo)


    @Test
    fun getAnimalsTest(){
        val testAnimal1 = DummyData().getTestAnimal()
        val testAnimal2 = DummyData().getTestAnimal()

        every { animalRepo.findAll() } answers {
            mutableListOf(testAnimal1, testAnimal2)
        }

        val animals = animalService.getAnimals()
        assert(animals.size == 2)
        assert(animals.first{it.name == testAnimal1.name}.breed == testAnimal1.breed)
    }

    @Test
    fun getAnimalsByNameTest(){
        val testAnimal1 = DummyData().getTestAnimalList()[4]
        val testAnimal2 = DummyData().getTestAnimalList()[0]

        every { animalRepo.findByNameIgnoreCase(any()) } answers {
            mutableListOf(testAnimal1, testAnimal2)
        }

        val animals = animalService.getAnimalsByName("tine")
        assert(animals?.size == 2)
        assert(animals?.first()?.name == "Tine")
    }

    @Test
    fun getAnimalsByStatusTest(){
        val testAnimal1 = DummyData().getTestAnimalList()[4]
        val testAnimal2 = DummyData().getTestAnimalList()[3]

        every { animalRepo.findByStatusId(any()) } answers {
            mutableListOf(testAnimal1, testAnimal2)
        }

        val animals = animalService.getAnimalsByStatus(0)
        assert(animals?.size == 2)
        assert(animals?.first()?.status?.name == testAnimal1.status.name)
    }

    @Test
    fun getAnimalsByBreedTest(){
        val testAnimal1 = DummyData().getTestAnimalList()[4]
        val testAnimal2 = DummyData().getTestAnimalList()[2]

        every { animalRepo.findByBreedIgnoreCase(any()) } answers {
            mutableListOf(testAnimal1, testAnimal2)
        }

        val animals = animalService.getAnimalsByBreed("Parrot")
        assert(animals?.size == 2)
        assert(animals?.first()?.breed == testAnimal1.breed)
    }

    @Test
    fun getAnimalByIdTest(){
        val testAnimal1 = DummyData().getTestAnimalList()[4]

        every { animalRepo.getById(any()) } answers {
            testAnimal1
        }

        val animals = animalService.getAnimalById(4)
        assert(animals?.name == testAnimal1.name)
        assert(animals?.id == testAnimal1.id)
    }

    @Test
    fun shouldRegisterAnimal(){
        every { animalRepo.save(any()) } answers {
            firstArg()
        }

        every { statusRepo.getById(any()) } answers {
            DummyData().getTestStatus()
        }

        val createAnimal = animalService.registerAnimal(DummyData().getTestAnimalDtoList()[0])

        assert(createAnimal?.name == "Trude")
        assert(createAnimal?.breed == "Meerkat")
    }

    @Test
    fun shouldUpdateAnimal(){

        val animal = DummyData().getTestAnimalList()[0]
        val animalDtoForUpdate = DummyData().getTestAnimalDtoForUpdateList()[0]

        every { animalRepo.save(any())} answers {
            firstArg()
        }

        every { animalRepo.existsById(any()) } answers {
            true
        }

        every { animalRepo.getById(any()) } answers {
            animal
        }

        every { statusRepo.getById(any()) } answers {
            DummyData().getTestStatus()
        }

        val updatedAnimal = animalService.updateAnimal(0, animalDtoForUpdate)

        assert(updatedAnimal?.name == animalDtoForUpdate.name)
        assert(updatedAnimal?.breed == animal.breed)
    }

    @Test
    fun shouldUpdateAnimalStatus(){
        val animal = DummyData().getTestAnimalList()[3]

        every { animalRepo.save(any())} answers {
            firstArg()
        }

        every { animalRepo.existsById(any()) } answers {
            true
        }

        every { statusRepo.existsById(any()) } answers {
            true
        }

        every { animalRepo.getById(any()) } answers {
            animal
        }

        every { statusRepo.getById(any()) } answers {
            DummyData().getTestStatus()
        }

        val updatedAnimal = animalService.updateAnimalStatus(0, 3)

        assert(updatedAnimal)
    }

    @Test
    fun shouldDeleteAnimal(){
        every { animalRepo.existsById(any()) } answers {
            true
        }

        every { animalRepo.deleteById(any()) } answers {
        }

        assert(animalService.deleteAnimal(0))
    }
}