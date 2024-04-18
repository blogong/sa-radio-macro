package net.bk24.macro.core.domain

import jakarta.persistence.*
import java.time.LocalDateTime

private const val MACRO_RUNNING_TIME = 1L

@Entity
@Table(name = "operation")
class Operation {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     var id: Long? = null

     @Column(nullable = false)
     var finishedAt: LocalDateTime = LocalDateTime.now().plusHours(MACRO_RUNNING_TIME)
}