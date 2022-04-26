package no.kristiania.eksamen.controller

import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.security.InvalidParameterException

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping("/all")
    fun getUsers(): ResponseEntity<List<UserEntity>>{
        return ResponseEntity.ok().body(userService.getUsers())
    }

    @PostMapping("/")
    fun registerUser(@RequestBody userDto: UserDto) : ResponseEntity<UserEntity>{
        val createdUser = userService.registerUser(userDto)
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString())
        return ResponseEntity.created(uri).body(createdUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long?){
        id?.let {
            if(!userService.deleteUser(it)) throw InvalidParameterException()
        }
    }
}