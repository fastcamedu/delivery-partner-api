package com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequest
import java.math.BigDecimal

data class DeliveryRequestDTO(
    val deliveryRequestId: Long,
    val orderId: Long,
    val storeId: Long,
    val customerId: Long,
    val storeAddress: String,
    val deliveryAddress: String,
    val deliveryStatus: DeliveryStatus,
    val deliveryPartnerId: Long?,
    val deliveryFee: BigDecimal,
) {
    companion object {
        fun of(it: DeliveryRequest): DeliveryRequestDTO {
            return DeliveryRequestDTO(
                deliveryRequestId = it.deliveryRequestId,
                orderId = it.orderId,
                storeId = it.storeId,
                customerId = it.customerId,
                storeAddress = it.storeAddress,
                deliveryAddress = it.deliveryAddress,
                deliveryStatus = it.deliveryStatus,
                deliveryPartnerId = it.deliveryPartnerId,
                deliveryFee = it.deliveryFee,
            )
        }
    }
}
