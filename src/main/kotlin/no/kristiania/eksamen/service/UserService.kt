package no.kristiania.eksamen.service

import no.kristiania.eksamen.controller.NewUserInfo
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.repo.AuthorityRepo
import no.kristiania.eksamen.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
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

    fun getAuthority(name: String): AuthorityEntity {
        return authorityRepo.findByName(name)
    }

    fun getUsers(): List<UserEntity> {
        return userRepo.findAll()
    }

    fun registerUser(newUserInfo: NewUserInfo): UserEntity {
        val newUser =
            UserEntity(email = newUserInfo.email, password = BCryptPasswordEncoder().encode(newUserInfo.password))
        newUser.authorities.add(getAuthority("USER"))

        return userRepo.save(newUser)
    }


}