package com.fastcampus.deliverypartnerapi.controller.deliverystatus.dto

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import java.math.BigDecimal

data class DeliveryStatusResponse(
    val deliveryRequestId: Long,
    val deliveryPartnerId: Long,
    val orderShortenId: String,
    val deliveryFee: BigDecimal,
    val changedDeliveryStatus: DeliveryStatus
)