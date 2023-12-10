package com.fastcampus.deliverypartnerapi.service.deliveryrequest

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import com.fastcampus.deliverypartnerapi.exception.NotFoundException
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequest
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequestRepository
import org.springframework.stereotype.Service

@Service
class DeliveryRequestCommandService(
    private val deliveryRequestRepository: DeliveryRequestRepository,
) {
    fun accept(deliveryRequestId: Long, deliveryPartnerId: Long) {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.DELIVERY_START
        this.deliveryRequestRepository.save(deliveryRequest)
    }

    fun reject(deliveryRequestId: Long, deliveryPartnerId: Long) {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.READY
        this.deliveryRequestRepository.save(deliveryRequest)
    }

    fun cancel(deliveryRequestId: Long, deliveryPartnerId: Long) {
        val deliveryRequest = findDeliveryRequest(deliveryRequestId)
        deliveryRequest.deliveryStatus = DeliveryStatus.READY
        this.deliveryRequestRepository.save(deliveryRequest)
    }

    private fun findDeliveryRequest(deliveryRequestId: Long): DeliveryRequest {
        val deliveryRequestOptional = deliveryRequestRepository.findById(deliveryRequestId)
        if (deliveryRequestOptional.isEmpty) {
            throw NotFoundException("배달 요청을 찾을 수 없습니다. $deliveryRequestId")
        }
        return deliveryRequestOptional.get()
    }
}

