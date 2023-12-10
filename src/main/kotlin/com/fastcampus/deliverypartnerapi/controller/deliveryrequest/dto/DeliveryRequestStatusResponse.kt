package com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryRequestStatus

data class DeliveryRequestStatusResponse(
    val orderDeliveryId: Long,
    val deliveryPartnerId: Long,
    val changedDeliveryStatus: DeliveryRequestStatus,
)
