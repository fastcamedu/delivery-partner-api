package com.fastcampus.deliverypartnerapi.repository.deliverypartner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryPartnerRepository: JpaRepository<DeliveryPartner, Long> {
    fun findByEmail(email: String): Optional<DeliveryPartner>
}