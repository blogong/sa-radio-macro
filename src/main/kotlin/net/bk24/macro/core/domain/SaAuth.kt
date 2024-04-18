package net.bk24.macro.core.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sa_auth")
class SaAuth(
     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     var id: Long? = null,
     @Column(unique = true, nullable = false)
     var code: String,
     @Column(nullable = false)
     var isBlocked: Boolean,
     @Column(unique = true)
     var snsId: String,
     @Column
     var postNo: String,
     @Column(length = 500)
     var memo: String,
     @Column(nullable = false)
     var createdAt: LocalDateTime = LocalDateTime.now(),
     @Column(nullable = false)
     var updatedAt: LocalDateTime = LocalDateTime.now()
)