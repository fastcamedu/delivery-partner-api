package com.fastcampus.deliverypartnerapi.repository.wallet

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryPartnerWalletRepository: JpaRepository<DeliveryPartnerWallet, Long> {
    fun findByDeliveryPartnerIdAndIsDeletedFalse(deliveryPartnerId: Long): Optional<DeliveryPartnerWallet>
}