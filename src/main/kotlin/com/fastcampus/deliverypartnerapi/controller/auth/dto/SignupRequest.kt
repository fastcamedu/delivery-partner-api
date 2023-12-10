package com.fastcampus.deliverypartnerapi.controller.auth.dto

import com.fastcampus.deliverypartnerapi.domain.bank.BankCode
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.Transportation

data class SignupRequest(
    val email: String,
    val password: String,
    val bankCode: BankCode,
    val bankAccountName: String,
    val bankAccount: String,
    val socialNumber: String,
    val deliveryZone: String,
    val transportation: Transportation,
)
