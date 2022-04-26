package no.kristiania.eksamen.unittests.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import no.kristiania.eksamen.service.AnimalService
import no.kristiania.eksamen.service.UserService
import no.kristiania.eksamen.testdata.DummyData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.security.InvalidParameterException

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AnimalControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {

        //If I don't add this everything crashes
        @Bean
        fun userService() = mockk<UserService>()

        @Bean
        fun animalService() = mockk<AnimalService>()
    }


    @Autowired
    private lateinit var animalService: AnimalService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAnimalsTest() {
        val animal1 = DummyData().getTestAnimal()
        val animal2 = DummyData().getTestAnimal()
        val animal3 = DummyData().getTestAnimal()

        every { animalService.getAnimals() } answers {
            mutableListOf(animal1, animal2, animal3)
        }

        mockMvc.get("/api/shelter")
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    fun shouldGetAllAnimalsByStatusCodeTest() {
        val animal1 = DummyData().getTestAnimalList()[3]
        val animal2 = DummyData().getTestAnimalList()[4]

        every { animalService.getAnimalsByStatus(any()) } answers {
            mutableListOf(animal1, animal2)
        }

        mockMvc.get("/api/shelter/status/0")
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    fun shouldGetAllAnimalsByBreedTest() {
        val animal1 = DummyData().getTestAnimalList()[2]
        val animal2 = DummyData().getTestAnimalList()[4]

        every { animalService.getAnimalsByBreed(any()) } answers {
            mutableListOf(animal1, animal2)
        }

        mockMvc.get("/api/shelter/breed/parrot")
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    fun shouldGetAllAnimalsByNameTest() {
        val animal1 = DummyData().getTestAnimalList()[0]
        val animal2 = DummyData().getTestAnimalList()[4]

        every { animalService.getAnimalsByName(any()) } answers {
            mutableListOf(animal1, animal2)
        }

        mockMvc.get("/api/shelter/name/tine")
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    fun shouldGetAnimalByIdTest() {
        val animal1 = DummyData().getTestAnimalList()[0]

        every { animalService.getAnimalById(any()) } answers {
            animal1
        }

        mockMvc.get("/api/shelter/0")
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    fun shouldRegisterAnimal(){
        every { animalService.registerAnimal(any()) } answers {
            DummyData().getTestAnimal()
        }

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/shelter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestAnimalDtoList()[0]))
                .characterEncoding("utf-8"))
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()
    }

    @Test
    fun shouldUpdateAnimalTest(){
        every { animalService.updateAnimal(any(), any()) } answers {
            DummyData().getTestAnimal()
        }

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/shelter/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestAnimalDtoForUpdateList()[0]))
                .characterEncoding("utf-8"))
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()
    }

    @Test
    fun shouldUpdateAnimalStatusTest() {
        every { animalService.updateAnimalStatus(any(), any()) } answers {
            true
        }

        every { animalService.getAnimalById(any()) } answers {
            DummyData().getTestAnimal()
        }

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/shelter/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"statusId\":2}")
                .characterEncoding("utf-8")
        )
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()
    }

    @Test
    fun shouldDeleteAnimalTest(){
        every { animalService.deleteAnimal(any()) } answers {
            true
        }

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/shelter/1")).andExpect { ResponseEntity.status(HttpStatus.OK) }.andReturn()

    }
}