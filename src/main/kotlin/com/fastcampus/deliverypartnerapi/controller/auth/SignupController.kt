package com.fastcampus.deliverypartnerapi.controller.auth

import com.fastcampus.deliverypartnerapi.controller.auth.dto.SignupRequest
import com.fastcampus.deliverypartnerapi.controller.auth.dto.SignupResponse
import com.fastcampus.deliverypartnerapi.service.delliverypartner.DeliveryPartnerService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignupController(
    private val deliveryPartnerService: DeliveryPartnerService,
) {
    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    @PostMapping("/apis/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<SignupResponse> {
        logger.info { ">>> 회원 가입 요청: $signupRequest" }

        val deliveryPartnerDetailsOptional = deliveryPartnerService.findByEmail(signupRequest.email)
        if (deliveryPartnerDetailsOptional.isPresent) {
            error("이미 가입된 배달파트너입니다. ${signupRequest.email}")
        }

        val deliveryPartner = deliveryPartnerService.signup(signupRequest)
        return ResponseEntity.ok(
            SignupResponse(
                deliveryPartnerId = deliveryPartner.deliveryPartnerId,
                email = deliveryPartner.email,
            )
        )
    }
}