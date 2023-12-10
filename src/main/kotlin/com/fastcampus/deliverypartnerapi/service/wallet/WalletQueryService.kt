package com.fastcampus.deliverypartnerapi.service.wallet

import com.fastcampus.deliverypartnerapi.exception.NotFoundException
import com.fastcampus.deliverypartnerapi.repository.wallet.DeliveryPartnerWalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class WalletQueryService(
    private val deliveryPartnerWalletRepository: DeliveryPartnerWalletRepository
) {
    fun balance(deliveryPartnerId: Long): BigDecimal {
        val deliveryPartnerWalletOptional =
            deliveryPartnerWalletRepository.findByDeliveryPartnerIdAndIsDeletedFalse(deliveryPartnerId)
        if (deliveryPartnerWalletOptional.isEmpty) {
            throw NotFoundException("배달 파트너의 지갑 정보를 찾을 수 없습니다. deliveryPartnerId = $deliveryPartnerId")
        }
        val deliveryPartnerWallet = deliveryPartnerWalletOptional.get()
        return deliveryPartnerWallet.deposit
    }
}