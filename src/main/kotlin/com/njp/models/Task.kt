package com.njp.models

import jakarta.persistence.*
import java.time.Instant


@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var description: String? = null,
    var isComplete: Boolean = false,

    var createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now()
)