package com.fastcampus.deliverypartnerapi.service.auth


import com.fastcampus.deliverypartnerapi.controller.auth.dto.AuthenticationRequest
import com.fastcampus.deliverypartnerapi.controller.auth.dto.AuthenticationResponse
import com.fastcampus.deliverypartnerapi.repository.token.RefreshToken
import com.fastcampus.deliverypartnerapi.repository.token.RefreshTokenRepository
import com.fastcampus.deliverypartnerapi.security.JwtProperties
import com.fastcampus.deliverypartnerapi.service.delliverypartner.DeliveryPartnerService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val deliveryPartnerService: DeliveryPartnerService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )
        val deliveryPartnerDetailsOptional = deliveryPartnerService.findByEmail(authenticationRequest.email)
        if (deliveryPartnerDetailsOptional.isEmpty) {
            error("배달 파트너 정보가 없습니다.")
        }

        val deliveryPartnerDetails = deliveryPartnerDetailsOptional.get()
        val accessToken = createAccessToken(deliveryPartnerDetails.email)
        val refreshToken = createRefreshToken(deliveryPartnerDetails.email)

        val refreshTokenOptional = refreshTokenRepository.findByEmail(deliveryPartnerDetails.email)
        if (refreshTokenOptional.isEmpty) {
            refreshTokenRepository.save(RefreshToken(email = deliveryPartnerDetails.email, refreshToken = refreshToken))
        } else {
            val savedRefreshToken = refreshTokenOptional.get()
            savedRefreshToken.refreshToken = refreshToken
            refreshTokenRepository.save(savedRefreshToken)
        }

        return AuthenticationResponse(
            deliveryPartnerId = deliveryPartnerDetails.deliveryPartnerId,
            email = authenticationRequest.email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedEmail = tokenService.extractEmail(refreshToken)

        return extractedEmail?.let { email ->
            val currentUserDetails = deliveryPartnerService.loadUserByUsername(email)
            val refreshTokenOptional = refreshTokenRepository.findByEmail(email)
            if (!tokenService.isExpired(refreshToken) && refreshTokenOptional.get().email == currentUserDetails.username) {
                createAccessToken(currentUserDetails.username)
            } else {
                null
            }
        }
    }

    private fun createAccessToken(email: String) = tokenService.generate(
        email = email,
        expirationDate = getAccessTokenExpiration()
    )

    private fun createRefreshToken(email: String) = tokenService.generate(
        email = email,
        expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}