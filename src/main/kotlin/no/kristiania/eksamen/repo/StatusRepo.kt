package no.kristiania.eksamen.repo

import no.kristiania.eksamen.model.StatusEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepo : JpaRepository<StatusEntity, Long> {
}