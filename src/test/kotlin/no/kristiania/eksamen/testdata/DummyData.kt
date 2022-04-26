package no.kristiania.eksamen.testdata

import no.kristiania.eksamen.dto.AnimalDto
import no.kristiania.eksamen.dto.AnimalDtoForUpdate
import no.kristiania.eksamen.dto.UserDto
import no.kristiania.eksamen.model.AnimalEntity
import no.kristiania.eksamen.model.AuthorityEntity
import no.kristiania.eksamen.model.StatusEntity
import no.kristiania.eksamen.model.UserEntity
import java.time.LocalDateTime.now

class DummyData {

    fun getTestStatusList(): List<StatusEntity> {

        return listOf(
            StatusEntity(0, "Dead"),
            StatusEntity(1, "Bad"),
            StatusEntity(2, "Ok"),
            StatusEntity(3, "Supreme"),
            StatusEntity(4, "Adopted")
        )

    }

    fun getTestStatus(): StatusEntity {
        return getTestStatusList().random()
    }

    fun getTestAnimalList(): List<AnimalEntity> {

        return listOf(
            AnimalEntity(0, "Tine", "Horse", 8, "She is fat", now(), getTestStatus()),
            AnimalEntity(1, "Geir", "Cow", 9, "Fine", now(), getTestStatus()),
            AnimalEntity(2, "Paul", "Parrot", 1, "So tired", now(), getTestStatus()),
            AnimalEntity(3, "Steve", "Cat", 3, "Dead", now(), getTestStatusList()[0]),
            AnimalEntity(4, "Tine", "Parrot", 8, "Dead", now(), getTestStatusList()[0])
        )
    }

    fun getTestAnimal(): AnimalEntity {
        return getTestAnimalList().random()
    }

    fun getTestAnimalDtoList() : List<AnimalDto> {

        return listOf(
            AnimalDto("Trude", "Meerkat", 3, "Got a cut in her paw", 2)
        )
    }

    fun getTestAnimalDtoForUpdateList() : List<AnimalDtoForUpdate> {

        return listOf(
            AnimalDtoForUpdate(name = "Trude", age = 3, breed=null, health = "Got a cut in her paw", statusId = 2)
        )
    }

    fun getTestAuthorityList(): List<AuthorityEntity> {

        return listOf(
            AuthorityEntity(0, "USER"),
            AuthorityEntity(1, "EMPLOYEE"),
            AuthorityEntity(2, "ADMIN")
        )
    }

    fun getTestAuthority(): AuthorityEntity {
        return getTestAuthorityList().random()
    }

    fun getTestUserList(): List<UserEntity> {

        return listOf(
            UserEntity(
                0,
                "bob@bob.no",
                "bobpassword",
                now(),
                true,
                mutableSetOf(getTestAuthority(), getTestAuthority()).toMutableList()
            ),
            UserEntity(
                1,
                "billy@bob.no",
                "billybobpassword",
                now(),
                true,
                mutableSetOf(getTestAuthority()).toMutableList()
            ),
            UserEntity(
                2,
                "bobbilly@bob.no",
                "bobpassword",
                now(),
                true,
                getTestAuthorityList().toMutableList()
            ),
            UserEntity(
                3,
                "bobbilly@bob.no",
                "bobpassword",
                now(),
                true
            )
        )
    }

    fun getTestUser(): UserEntity {
        return getTestUserList().random()
    }

    fun getTestUserDtoList(): List<UserDto> {
        return listOf(
            UserDto(email = "great@email.com", password = "greatpassword"),
            UserDto(email = "greater@email.com", password = "shitpassword")
        )
    }
}