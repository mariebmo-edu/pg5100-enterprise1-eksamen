package no.kristiania.eksamen.integrationtests

import io.mockk.mockk
import no.kristiania.eksamen.controller.NewUserInfo
import no.kristiania.eksamen.service.AnimalService
import no.kristiania.eksamen.service.AuthorityService
import no.kristiania.eksamen.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(UserService::class)
class DatabaseIntegrationTests(@Autowired private val userService: UserService, ) {

    @TestConfiguration
    class ControllerTestConfig {

        //If I don't add this everything crashes
        @Bean
        fun animalService() = mockk<AnimalService>()

        @Bean
        fun authorityService() = mockk<AuthorityService>()
    }

    @Test
    fun shouldGetUsers(){
        val result = userService.getUsers()
        assert(result.size == 2)
    }

    @Test
    fun shouldRegisterAndFindUser(){

        val newUserInfo = NewUserInfo("testuser1@test.com", "password1")
        val createdUser = userService.registerUser(newUserInfo)

        assert(createdUser.email == "testuser1@test.com")

        val foundUser = userService.loadUserByUsername("testuser1@test.com")

        assert(foundUser.username == createdUser.email)
    }
}