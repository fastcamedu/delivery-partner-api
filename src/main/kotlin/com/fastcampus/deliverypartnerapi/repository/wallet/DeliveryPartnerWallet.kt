package com.fastcampus.deliverypartnerapi.repository.wallet

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "delivery_partner_wallets", schema = "delivery_partner")
data class DeliveryPartnerWallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_partner_wallet_id")
    val deliveryPartnerWalletId: Long = 0L,
    @Column(name = "delivery_partner_id")
    val deliveryPartnerId: Long,
    @Column(name = "deposit")
    var deposit: BigDecimal,
): BaseEntity()
