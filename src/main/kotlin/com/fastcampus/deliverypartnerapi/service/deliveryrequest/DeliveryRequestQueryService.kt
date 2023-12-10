package com.fastcampus.deliverypartnerapi.service.deliveryrequest

import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequest
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequestRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeliveryRequestQueryService(
    private val deliveryRequestRepository: DeliveryRequestRepository
) {
    fun findAllByDeliveryAddressContains(address: String): List<DeliveryRequest> {
        return this.deliveryRequestRepository.findAllByDeliveryAddressContains(address)
    }

    fun findById(orderDeliveryRequestId: Long): Optional<DeliveryRequest> {
        return deliveryRequestRepository.findById(orderDeliveryRequestId)
    }
}