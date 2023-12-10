package com.fastcampus.deliverypartnerapi.controller.auth.dto

data class AuthenticationRequest(
    val email: String,
    val password: String,
)