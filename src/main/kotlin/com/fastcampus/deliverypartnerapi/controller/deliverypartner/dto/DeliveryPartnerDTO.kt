package com.fastcampus.deliverypartnerapi.controller.deliverypartner.dto

import com.fastcampus.deliverypartnerapi.domain.bank.BankCode
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.DeliveryPartnerStatus
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.Transportation
import com.fastcampus.deliverypartnerapi.repository.deliverypartner.DeliveryPartner

data class DeliveryPartnerDTO(
    val deliveryPartnerId: Long,
    val email: String,
    val transportation: Transportation,
    val deliveryZone: String,
    val bankCode: BankCode,
    val bankAccount: String,
    val bankName: String,
    val deliveryPartnerStatus: DeliveryPartnerStatus = DeliveryPartnerStatus.ACTIVE,
) {
    companion object {
        fun of(deliveryPartner: DeliveryPartner): DeliveryPartnerDTO {
            return DeliveryPartnerDTO(
                deliveryPartnerId = deliveryPartner.deliveryPartnerId,
                email = deliveryPartner.email,
                transportation = deliveryPartner.transportation,
                deliveryZone = deliveryPartner.deliveryZone,
                bankCode = deliveryPartner.bankCode,
                bankAccount = deliveryPartner.bankAccount,
                bankName = deliveryPartner.bankAccountName,
                deliveryPartnerStatus = deliveryPartner.deliveryPartnerStatus,
            )
        }
    }
}
