package no.kristiania.eksamen.model

import javax.persistence.*

@Entity
@Table(name = "status")
class StatusEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_status_id_seq")
    @SequenceGenerator(
        name = "status_status_id_seq",
        allocationSize = 1
    )
    @Column(name = "status_id")
    val id: Long? = null,

    @Column(name = "status_name")
    val name: String
){}