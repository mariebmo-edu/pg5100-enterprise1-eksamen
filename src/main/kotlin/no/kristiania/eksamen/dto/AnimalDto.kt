package no.kristiania.eksamen.dto

data class AnimalDto(
    val name: String? = null,
    val breed: String,
    val age: Int? = null,
    val health: String = "",
    val statusId: Long = 5
)

data class AnimalDtoForUpdate(
    val name: String?,
    val breed: String?,
    val age: Int?,
    val health: String?,
    val statusId: Long?
)
