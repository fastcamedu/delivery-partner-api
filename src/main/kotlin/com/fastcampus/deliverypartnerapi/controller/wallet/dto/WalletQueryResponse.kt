package com.fastcampus.deliverypartnerapi.controller.wallet.dto

import java.math.BigDecimal

data class WalletQueryResponse(
    val deliveryPartnerId: Long,
    val balance: BigDecimal,
)