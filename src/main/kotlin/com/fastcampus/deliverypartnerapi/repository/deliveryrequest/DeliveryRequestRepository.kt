package com.fastcampus.deliverypartnerapi.repository.deliveryrequest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRequestRepository : JpaRepository<DeliveryRequest, Long> {
    fun findAllByDeliveryAddressContains(deliveryAddress: String): List<DeliveryRequest>
}