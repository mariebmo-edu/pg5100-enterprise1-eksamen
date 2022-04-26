package no.kristiania.eksamen.unittests.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.service.AnimalService
import no.kristiania.eksamen.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

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
    fun shouldGetAllUsers(){
        val testUser1 = UserEntity(email = "test1@user.com", password =  "password1")

        every { userService.getUsers() } answers {
            mutableListOf(testUser1)
        }

        mockMvc.get("/api/user/all")
            .andExpect { status { isOk() } }
    }



}