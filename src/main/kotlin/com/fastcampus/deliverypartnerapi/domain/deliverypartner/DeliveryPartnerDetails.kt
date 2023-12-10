package com.fastcampus.deliverypartnerapi.domain.deliverypartner

data class DeliveryPartnerDetails(
    val deliveryPartnerId: Long,
    val email: String,
    val password: String,
    val role: DeliveryPartnerRole,
)
