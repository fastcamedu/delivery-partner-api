package com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryRequestStatus

data class DeliveryRequestStatusRequest(
    val deliveryRequestId: Long,
    val deliveryPartnerId: Long,
    val deliveryRequestStatus: DeliveryRequestStatus,
)
