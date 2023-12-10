package com.fastcampus.deliverypartnerapi.controller.wallet.dto

import java.math.BigDecimal

data class WithdrawalRequest(
    val deliveryPartnerId: Long,
    val withdrawalAmount: BigDecimal,
)
