package com.fastcampus.deliverypartnerapi.repository.deliverypartner

import com.fastcampus.deliverypartnerapi.domain.bank.BankCode
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.DeliveryPartnerRole
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.DeliveryPartnerStatus
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.Transportation
import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "delivery_partners", schema = "delivery_partner")
data class DeliveryPartner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_partner_id")
    val deliveryPartnerId: Long = 0L,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "transportation")
    val transportation: Transportation,

    @Column(name = "delivery_zone")
    val deliveryZone: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_code")
    val bankCode: BankCode,

    @Column(name = "bank_account")
    val bankAccount: String,

    @Column(name = "bank_account_name")
    val bankAccountName: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val deliveryPartnerStatus: DeliveryPartnerStatus = DeliveryPartnerStatus.ACTIVE,

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_partner_role")
    val deliveryPartnerRole: DeliveryPartnerRole

): BaseEntity()
