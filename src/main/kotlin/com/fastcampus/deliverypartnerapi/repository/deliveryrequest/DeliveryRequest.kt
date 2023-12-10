package com.fastcampus.deliverypartnerapi.repository.deliveryrequest

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.BaseEntity
import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "delivery_requests", schema = "delivery_partner")
data class DeliveryRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_request_id")
    val deliveryRequestId: Long = 0L,

    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "order_shorten_id")
    val orderShortenId: String,

    @Column(name = "store_id")
    val storeId: Long,

    @Column(name = "customer_id")
    val customerId: Long,

    @Column(name = "store_address")
    val storeAddress: String,

    @Column(name = "delivery_partner_id")
    var deliveryPartnerId: Long?,

    @Column(name = "delivery_address")
    val deliveryAddress: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    var deliveryStatus: DeliveryStatus,

    @Column(name = "delivery_fee")
    val deliveryFee: BigDecimal,
): BaseEntity()
