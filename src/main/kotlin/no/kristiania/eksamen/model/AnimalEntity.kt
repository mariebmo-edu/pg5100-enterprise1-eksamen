package no.kristiania.eksamen.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "animals")
class AnimalEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animals_animal_id_seq")
    @SequenceGenerator(
        name = "animals_animal_id_seq",
        allocationSize = 1
    )
    @Column(name = "animal_id")
    val id: Long? = null,

    //an animal might not have a name yet due to being new to the shelter
    @Column(name = "animal_name")
    val name: String? = null,

    @Column(name = "animal_breed")
    val breed: String,

    //an animal arriving in a shelter might not have a known age
    @Column(name = "animal_age")
    val age: Int? = null,

    @Column(name = "animal_health")
    val health: String,

    @Column(name = "animal_created")
    val created: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    val status: StatusEntity

) {
}