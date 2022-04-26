package no.kristiania.eksamen.unittests.service

import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertFalse
import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.repo.AuthorityRepo
import no.kristiania.eksamen.repo.UserRepo
import no.kristiania.eksamen.service.UserService
import no.kristiania.eksamen.testdata.DummyData
import org.junit.Test
import java.util.*

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val userService = UserService(userRepo, authorityRepo)

    @Test
    fun getAuthoritiesTest() {
        val authority1 = DummyData().getTestAuthority()
        val authority2 = DummyData().getTestAuthority()

        every { authorityRepo.findAll() } answers {
            mutableListOf(authority1, authority2)
        }

        val authorities = userService.getAuthorities()
        assert(authorities.size == 2)
        assert(authorities.first { it.name == authority1.name }.id == authority1.id)
    }

    @Test
    fun getAuthorityTest() {
        val authority1 = DummyData().getTestAuthorityList()[0]

        every { authorityRepo.findByName(any()) } answers {
            authority1
        }

        val authority = userService.getAuthority("USER")
        assert(authority?.name == "USER")
        assert(authority?.id == authority1.id)
    }

    @Test
    fun getUsersTest() {
        val testUser1 = DummyData().getTestUser()
        val testUser2 = DummyData().getTestUser()

        every { userRepo.findAll() } answers {
            mutableListOf(testUser1, testUser2)
        }

        val users = userService.getUsers()
        assert(users.size == 2)
        assert(users.first { it.email == testUser1.email }.password == testUser1.password)

    }

    @Test
    fun shouldRegisterUserTest() {
        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { authorityRepo.findByName(any()) } answers {
            DummyData().getTestAuthority()
        }

        val createUser = userService.registerUser(DummyData().getTestUserDtoList()[0])

        assert(createUser?.email == DummyData().getTestUserDtoList()[0].email)
        assert(createUser?.enabled == true)
    }

    @Test
    fun grantAuthorityTest() {

        val user = DummyData().getTestUser()

        every { authorityRepo.existsById(any()) } answers { true }
        every { userRepo.findById(any()) } answers {
            Optional.of(user)
        }

        every { authorityRepo.getById(any()) } answers {
            DummyData().getTestAuthorityList()[1]
        }

        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every {authorityRepo.findByName(any())} answers {
            DummyData().getTestAuthorityList()[1]
        }

        assert(userService.grantAuthority(user.id!!, 1))
        assert(userService.grantAuthority(user.id!!, "EMPLOYEE"))
    }

    @Test
    fun registerEmployeeTest(){

        val user = DummyData().getTestUserDtoList()[0]
        val authority = DummyData().getTestAuthorityList()[1]

        every { userRepo.save(any()) } answers {firstArg()}
        every {authorityRepo.findByName(any())} answers {authority}

        val registeredUser = userService.registerEmployee(user)

        if (registeredUser != null) {
            assert(registeredUser.authorities.contains(authority))
            assert(registeredUser.email == user.email)
        }
    }

    @Test
    fun deleteAnyUserTest(){
        every { userRepo.existsById(any()) } answers {
            true
        }

        every { userRepo.deleteById(any()) } answers {
        }

        assert(userService.deleteAnyUser(0))
    }

    @Test
    fun deleteUserTest(){
        every { userRepo.existsById(any()) } answers {
            true
        }

        every { userRepo.getById(0) } answers {
            DummyData().getTestUserList()[2]
        }

        every { authorityRepo.findByName(any()) } answers {
            DummyData().getTestAuthorityList()[0]
        }

        every { userRepo.getById(1) } answers {
            DummyData().getTestUserList()[3]
        }

        every { userRepo.deleteById(any()) } answers {
        }

        assert(!userService.deleteUser(0))
        assert(userService.deleteAnyUser(1))
    }
}