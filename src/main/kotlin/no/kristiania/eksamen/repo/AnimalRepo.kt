package no.kristiania.eksamen.repo

import no.kristiania.eksamen.model.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnimalRepo : JpaRepository<AnimalEntity, Long> {

    fun findByStatusId(statusId : Long) : List<AnimalEntity>

    fun findByNameIgnoreCase(name: String) : List<AnimalEntity>

    fun findByBreedIgnoreCase(breed: String) : List<AnimalEntity>
}