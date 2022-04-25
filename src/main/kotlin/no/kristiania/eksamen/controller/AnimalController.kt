package no.kristiania.eksamen.controller

import no.kristiania.eksamen.model.AnimalEntity
import no.kristiania.eksamen.service.AnimalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/shelter")
class AnimalController(@Autowired private val animalService: AnimalService) {

    @GetMapping("/all")
    fun getAnimals():ResponseEntity<List<AnimalEntity>>{
        return ResponseEntity.ok().body(animalService.getAnimals())
    }

    @GetMapping("/all/status")
    fun getAnimalsByStatusCode(@RequestParam statusId : String):ResponseEntity<List<AnimalEntity>>{
        return ResponseEntity.ok().body(animalService.getAnimalsByStatus(statusId.toLong()))
    }

    @GetMapping("/all/breed")
    fun getAnimalsByBreed(@RequestParam breed : String):ResponseEntity<List<AnimalEntity>>{
        return ResponseEntity.ok().body(animalService.getAnimalsByBreed(breed))
    }

    @GetMapping("/all/name")
    fun getAnimalsByName(@RequestParam name : String):ResponseEntity<List<AnimalEntity>>{
        return ResponseEntity.ok().body(animalService.getAnimalsByName(name))
    }

    @GetMapping("/id")
    fun getAnimalByID(@RequestParam id : String):ResponseEntity<AnimalEntity>{
        return ResponseEntity.ok().body(animalService.getAnimalById(id.toLong()))
    }

    @PostMapping("/new")
    fun registerAnimal(@RequestBody newAnimalInfo : NewAnimalInfo) : ResponseEntity<AnimalEntity>{
        val createdAnimal = animalService.registerAnimal(newAnimalInfo)
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/animal/new").toUriString())
        return ResponseEntity.created(uri).body(createdAnimal)
    }
}

data class NewAnimalInfo(val name: String? = null, val breed: String, val age: Int? = null, val health: String = "", val statusId: Long = 5)