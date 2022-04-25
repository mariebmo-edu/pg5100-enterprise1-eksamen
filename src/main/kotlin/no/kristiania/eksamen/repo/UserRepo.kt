package no.kristiania.eksamen.repo

import no.kristiania.eksamen.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo:JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?
}