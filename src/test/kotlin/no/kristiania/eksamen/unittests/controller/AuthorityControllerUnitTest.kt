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

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AuthorityControllerUnitTest {

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
    fun shouldGetAllAuthoritiesTest(){
        val authorities = DummyData().getTestAuthorityList()

        every { userService.getAuthorities() } answers {
            authorities
        }

        mockMvc.get("/api/authority")
            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldGrantAuthorityTest(){
        every { userService.grantAuthority(any(), 2) } answers {true}

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/authority/grant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"authorityId\":2}")
                .characterEncoding("utf-8")
        )
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()
    }

    @Test
    fun shouldAddEmployeeTest(){
        every { userService.registerEmployee(any()) } answers {
            DummyData().getTestUser()
        }

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/authority/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestUserDtoList()[0]))
                .characterEncoding("utf-8"))
            .andExpect { ResponseEntity.status(HttpStatus.OK) }
            .andReturn()
    }

    @Test
    fun shouldDeleteUserTest(){
        every { userService.deleteAnyUser(any()) } answers {
            true
        }

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/authority/employee/1")).andExpect { ResponseEntity.status(HttpStatus.OK) }.andReturn()

    }
}