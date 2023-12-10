package com.fastcampus.deliverypartnerapi.service.delliverypartner

import com.fastcampus.deliverypartnerapi.controller.auth.dto.SignupRequest
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.DeliveryPartnerDetails
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.DeliveryPartnerRole
import com.fastcampus.deliverypartnerapi.domain.deliverypartner.DeliveryPartnerStatus
import com.fastcampus.deliverypartnerapi.exception.NotFoundException
import com.fastcampus.deliverypartnerapi.repository.deliverypartner.DeliveryPartner
import com.fastcampus.deliverypartnerapi.repository.deliverypartner.DeliveryPartnerRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeliveryPartnerService(
    private val deliveryPartnerRepository: DeliveryPartnerRepository,
    private val passwordEncoder: PasswordEncoder,
): UserDetailsService {

    fun findById(deliveryPartnerId: Long): Optional<DeliveryPartner> {
        return deliveryPartnerRepository.findById(deliveryPartnerId)
    }

    fun findByEmail(email: String): Optional<DeliveryPartnerDetails> {
        val deliveryPartnerOptional = this.deliveryPartnerRepository.findByEmail(email = email)
        if (deliveryPartnerOptional.isEmpty) {
            return Optional.empty<DeliveryPartnerDetails>()
        }
        val deliveryPartner = deliveryPartnerOptional.get()
        return Optional.of(DeliveryPartnerDetails(
            deliveryPartnerId = deliveryPartner.deliveryPartnerId,
            email = deliveryPartner.email,
            password = deliveryPartner.password,
            role = deliveryPartner.deliveryPartnerRole,
        ))
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        if (email == null) {
            throw NotFoundException("배달 파트너 정보를 찾을 수 없습니다. $email")
        }
        val deliveryPartnerOptional = this.deliveryPartnerRepository.findByEmail(email)
        if (deliveryPartnerOptional.isEmpty) {
            throw NotFoundException("배달 파트너 정보를 찾을 수 없습니다. $email")
        }
        val deliveryPartner = deliveryPartnerOptional.get()
        return User.builder()
            .username(deliveryPartner.email)
            .password(deliveryPartner.password)
            .roles(DeliveryPartnerRole.BASIC.name)
            .build()
    }

    fun signup(signupRequest: SignupRequest): DeliveryPartner {
        val deliveryPartner = DeliveryPartner(
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            bankCode = signupRequest.bankCode,
            bankAccount = signupRequest.bankAccount,
            bankAccountName = signupRequest.bankAccountName,
            deliveryPartnerRole = DeliveryPartnerRole.BASIC,
            deliveryPartnerStatus = DeliveryPartnerStatus.ACTIVE,
            deliveryZone = signupRequest.deliveryZone,
            transportation = signupRequest.transportation,
        )
        return this.deliveryPartnerRepository.save(deliveryPartner)
    }
}