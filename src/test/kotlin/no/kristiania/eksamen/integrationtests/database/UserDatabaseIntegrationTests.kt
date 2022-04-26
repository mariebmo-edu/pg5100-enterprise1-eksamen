package no.kristiania.eksamen.integrationtests.database

import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
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
        assert(result.size > 2)
    }

    @Test
    fun shouldRegisterAndFindUser(){

        val newUserInfo = UserDto("testuser1@test.com", "password1")
        val createdUser = userService.registerUser(newUserInfo)

        assert(createdUser?.email == "testuser1@test.com")

        val foundUser = userService.loadUserByUsername("testuser1@test.com")

        assert(foundUser.username == createdUser?.email)
    }

    @Test
    fun shouldDeleteUser(){

        assert(!userService.deleteUser(1))
        assert(userService.deleteUser(3))

    }

    @Test
    fun shouldUpdateUser(){
        userService.grantAuthority(2, 1)

        val user = userService.getUserById(2)
        val authority = userService.getAuthority(1)

        assert(user!!.authorities.contains(authority))
    }
}