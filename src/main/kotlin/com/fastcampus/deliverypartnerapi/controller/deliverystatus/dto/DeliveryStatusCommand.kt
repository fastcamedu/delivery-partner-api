package com.fastcampus.deliverypartnerapi.controller.deliverystatus.dto

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus

data class DeliveryStatusCommand(
    val deliveryRequestId: Long,
    val deliveryPartnerId: Long,
    val deliveryStatus: DeliveryStatus,
)