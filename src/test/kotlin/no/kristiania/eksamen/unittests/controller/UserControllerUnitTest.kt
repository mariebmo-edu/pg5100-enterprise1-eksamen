package no.kristiania.eksamen.unittests.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.mockk.every
import io.mockk.mockk
import no.kristiania.eksamen.model.UserEntity
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
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerUnitTest {


    @TestConfiguration
    class ControllerTestConfig {

        @Bean
        fun userService() = mockk<UserService>()


        //If I don't add this everything crashes
        @Bean
        fun animalService() = mockk<AnimalService>()
    }


    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllUsersTest(){
        val testUser1 = DummyData().getTestUser()
        val testUser2 = DummyData().getTestUser()
        val testUser3 = DummyData().getTestUser()

        every { userService.getUsers() } answers {
            mutableListOf(testUser1, testUser2, testUser3)
        }

        mockMvc.get("/api/user/all")
            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldRegisterUserTest(){
        every { userService.registerUser(any()) } answers {
            DummyData().getTestUser()
        }

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestUserDtoList()[0]))
                .characterEncoding("utf-8"))
            .andExpect { status(HttpStatus.OK) }
            .andReturn()
    }

    @Test
    fun shouldDeleteUserTest(){
        every { userService.deleteUser(any()) } answers {
           true
        }

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/user/1")).andExpect { status(HttpStatus.OK) }.andReturn()

    }

}