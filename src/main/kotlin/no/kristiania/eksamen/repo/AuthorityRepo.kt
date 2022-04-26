package no.kristiania.eksamen.repo

import no.kristiania.eksamen.model.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepo: JpaRepository<AuthorityEntity, Long> {

    fun findByName(name: String) : AuthorityEntity?
}