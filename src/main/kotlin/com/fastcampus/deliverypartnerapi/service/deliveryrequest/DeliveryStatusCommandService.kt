package com.fastcampus.deliverypartnerapi.service.deliveryrequest

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import com.fastcampus.deliverypartnerapi.exception.NotFoundException
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequest
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequestRepository
import org.springframework.stereotype.Service

@Service
class DeliveryStatusCommandService(
    private val deliveryRequestRepository: DeliveryRequestRepository
) {
    fun deliveryStart(deliveryRequestId: Long, deliveryPartnerId: Long): DeliveryRequest {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.DELIVERY_START
        return this.deliveryRequestRepository.save(deliveryRequest)
    }

    fun arrivalAtStore(deliveryRequestId: Long, deliveryPartnerId: Long): DeliveryRequest {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.ARRIVAL_AT_STORE
        return this.deliveryRequestRepository.save(deliveryRequest)
    }

    fun pickupComplete(deliveryRequestId: Long, deliveryPartnerId: Long): DeliveryRequest {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.PICKUP_COMPLETE
        return this.deliveryRequestRepository.save(deliveryRequest)
    }

    fun deliveryComplete(deliveryRequestId: Long, deliveryPartnerId: Long): DeliveryRequest {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.DELIVERY_COMPLETE
        return this.deliveryRequestRepository.save(deliveryRequest)
    }

    private fun findDeliveryRequest(deliveryRequestId: Long): DeliveryRequest {
        val deliveryRequestOptional = deliveryRequestRepository.findById(deliveryRequestId)
        if (deliveryRequestOptional.isEmpty) {
            throw NotFoundException("배달 요청을 찾을 수 없습니다. $deliveryRequestId")
        }
        return deliveryRequestOptional.get()
    }
}
