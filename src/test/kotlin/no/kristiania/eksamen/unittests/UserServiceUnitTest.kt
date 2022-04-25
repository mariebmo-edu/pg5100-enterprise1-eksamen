package no.kristiania.eksamen.unittests

import io.mockk.every
import io.mockk.mockk
import no.kristiania.eksamen.controller.NewUserInfo
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.repo.AuthorityRepo
import no.kristiania.eksamen.repo.UserRepo
import no.kristiania.eksamen.service.UserService
import org.junit.Test

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val userService = UserService(userRepo, authorityRepo)

    @Test
    fun getUsersTest(){
        val testUser1 = UserEntity(email = "test1@user.com", password =  "password1")
        val testUser2 = UserEntity(email = "test2@user.com", password =  "password2")

        every { userRepo.findAll() } answers {
            mutableListOf(testUser1, testUser2)
        }

        val users = userService.getUsers()
        assert(users.size == 2)
        assert(users.first{it.email == "test1@user.com"}.password == "password1")

    }

    @Test
    fun shouldRegisterUser(){
        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { authorityRepo.findByName(any())} answers {
            AuthorityEntity(name = "USER")
        }

        val createUser = userService.registerUser(NewUserInfo("test3@user.com", "password3"))

        assert(createUser.email == "test3@user.com")
        assert(createUser.enabled == true)
    }
}