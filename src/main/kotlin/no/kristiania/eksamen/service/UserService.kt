package no.kristiania.eksamen.service

import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.model.AnimalEntity
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.repo.AuthorityRepo
import no.kristiania.eksamen.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.beans
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepo: UserRepo, @Autowired private val authorityRepo: AuthorityRepo) :
    UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByEmail(it)
            return User(user.email, user.password, user.authorities.map { SimpleGrantedAuthority(it.name) })
        }

        throw Exception("DAFUQ ARE YOU DOING")
    }

    fun getAuthorities(): List<AuthorityEntity> {
        return authorityRepo.findAll()
    }

    fun getAuthority(name: String): AuthorityEntity? {
        return authorityRepo.findByName(name)
    }

    fun getAuthority(id: Long): AuthorityEntity? {
        return authorityRepo.getById(id)
    }

    fun getUsers(): MutableList<UserEntity> {
        return userRepo.findAll()
    }

    fun getUserById(id : Long) : UserEntity?{
        return userRepo.getById(id)
    }

    fun registerUser(userDto: UserDto): UserEntity? {
        return try {
            val newUser =
                UserEntity(email = userDto.email, password = BCryptPasswordEncoder().encode(userDto.password))
            getAuthority("USER")?.let { newUser.authorities.add(it) }.let {
                userRepo.save(newUser)
            }
        } catch (error: Error) {
            null
        }
    }

    fun grantAuthority(id: Long, authorityId: Long): Boolean {
        authorityRepo.existsById(authorityId).let {
            userRepo.findById(id).orElse(null)?.let {

                it.authorities.add(authorityRepo.getById(authorityId))

                userRepo.save(it)
                return true
            }
        }
        return false
    }

    fun grantAuthority(id: Long, authorityName: String): Boolean {
        authorityRepo.findByName(authorityName)?.let {
            userRepo.findById(id).orElse(null)?.let {

                getAuthority(authorityName)?.let { auth -> it.authorities.add(auth) }

                userRepo.save(it)
                return true
            }
        }
        return false
    }

    fun registerEmployee(userDto: UserDto): UserEntity? {
         try {
            registerUser(userDto)?.let {
                getAuthority("EMPLOYEE")?.let { auth -> it.authorities.add(auth) }
                return userRepo.save(it)
            }
            return null
        } catch (error: Error) {
            return null
        }
    }

    fun deleteAnyUser(id: Long): Boolean {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id)
            return true
        }
        return false
    }

    fun deleteUser(id: Long): Boolean {
        if (userRepo.existsById(id)) {
            val user = userRepo.getById(id)

            if (user.authorities.size < 2 && user.authorities.contains(authorityRepo.findByName("USER"))) {
                userRepo.deleteById(id)
                return true
            }
        }
        return false
    }


}