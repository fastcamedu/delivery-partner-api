package com.fastcampus.deliverypartnerapi.controller.wallet.dto

import java.math.BigDecimal

data class WithdrawalResponse(
    val deliveryPartnerId: Long,
    val originalBalance: BigDecimal,
    val withdrawalBalance: BigDecimal,
    val balance: BigDecimal
)
