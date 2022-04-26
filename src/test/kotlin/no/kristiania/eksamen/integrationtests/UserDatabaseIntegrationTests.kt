package no.kristiania.eksamen.integrationtests

import io.mockk.mockk
import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.service.AnimalService
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
class UserDatabaseIntegrationTests(@Autowired private val userService: UserService, ) {


    @Test
    fun shouldGetUsers(){
        val result = userService.getUsers()
        assert(result.size == 3)
    }

    @Test
    fun shouldRegisterAndFindUser(){

        val newUserInfo = UserDto("testuser1@test.com", "password1")
        val createdUser = userService.registerUser(newUserInfo)

        assert(createdUser?.email == "testuser1@test.com")

        val foundUser = userService.loadUserByUsername("testuser1@test.com")

        assert(foundUser.username == createdUser?.email)
    }
}