package no.kristiania.eksamen.controller

import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/user")
class AuthController(@Autowired private val userService: UserService) {

    @GetMapping("/authority/all")
    fun getAuthorities(): ResponseEntity<List<AuthorityEntity>>{
        return ResponseEntity.ok().body(userService.getAuthorities())
    }

    @GetMapping("/all")
    fun getUsers(): ResponseEntity<List<UserEntity>>{
        return ResponseEntity.ok().body(userService.getUsers())
    }

    @PostMapping("/new")
    fun registerUser(@RequestBody newUserInfo : NewUserInfo) : ResponseEntity<UserEntity>{
        val createdUser = userService.registerUser(newUserInfo)
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString())
        return ResponseEntity.created(uri).body(createdUser)
    }
}

data class NewUserInfo(val email: String, val password: String)