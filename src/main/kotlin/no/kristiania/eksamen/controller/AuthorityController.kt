package no.kristiania.eksamen.controller

import com.fasterxml.jackson.databind.JsonNode
import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.UserEntity
import no.kristiania.eksamen.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.security.InvalidParameterException

@RestController
@RequestMapping("/api/authority")
class AuthorityController(@Autowired private val userService: UserService) {

    @GetMapping("")
    fun getAuthorities(): ResponseEntity<List<AuthorityEntity>> {
        return ResponseEntity.ok().body(userService.getAuthorities())
    }

    @PatchMapping("/grant/{id}")
    fun grantAuthority(@PathVariable id: Long?, @RequestBody authorityId: JsonNode){
        id?.let {
            if(!userService.grantAuthority(it, authorityId.get("authorityId").asLong())) throw InvalidParameterException()
        }
    }

    @PostMapping("/employee")
    fun addEmployee(@RequestBody userDto: UserDto?) : ResponseEntity<Any>?{
        when(userDto){
            null -> throw InvalidParameterException()
            else -> {
                userService.registerEmployee(userDto)?.let {
                    val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authority/employee").toUriString())
                    return ResponseEntity.created(uri).body(it)
                }.run{return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request")}
            }
        }
    }

    @DeleteMapping("/employee/{id}")
    fun deleteEmployee(@PathVariable id: Long?){
        id?.let {
            if(!userService.deleteAnyUser(it)) throw InvalidParameterException()
        }
    }

}