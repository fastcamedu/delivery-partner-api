package com.fastcampus.deliverypartnerapi.domain.deliveryrequest

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.OffsetDateTime

@MappedSuperclass
open class BaseEntity {
    @Column(name = "is_deleted")
    val isDeleted: Boolean = false

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "updated_at")
    val updatedAT: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "created_by")
    var createdBy: String = ""

    @Column(name = "updated_by")
    var updatedBy: String = ""
}
