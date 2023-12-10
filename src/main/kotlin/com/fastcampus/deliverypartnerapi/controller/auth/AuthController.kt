package com.fastcampus.deliverypartnerapi.controller.auth

import com.fastcampus.deliverypartnerapi.controller.auth.dto.RefreshTokenRequest
import com.fastcampus.deliverypartnerapi.controller.auth.dto.TokenResponse
import com.fastcampus.deliverypartnerapi.common.HttpConstants.Companion.COOKIE_NAME_ACCESS_TOKEN
import com.fastcampus.deliverypartnerapi.controller.auth.dto.AuthenticationRequest
import com.fastcampus.deliverypartnerapi.controller.auth.dto.AuthenticationResponse
import com.fastcampus.deliverypartnerapi.service.auth.AuthenticationService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping
class AuthController(
    private val authenticationService: AuthenticationService
) {
    companion object {
        private val logger = KotlinLogging.logger {  }
    }

    @PostMapping("/apis/auth")
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<AuthenticationResponse> {
        logger.info { ">>> Request login: ${authRequest.email}" }
        val authenticationResponse = authenticationService.authentication(authRequest)
        val cookie = createAuthCookie(authenticationResponse.accessToken)
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(authenticationResponse)
    }

    private fun createAuthCookie(accessToken: String): Cookie {
        val cookie = Cookie(COOKIE_NAME_ACCESS_TOKEN, accessToken)
        cookie.path = "/"
        cookie.maxAge = 60 * 60 * 24 * 7
        return cookie
    }

    @PostMapping("/apis/auth/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse {
        logger.info { ">>> Request refresh accessToken" }
        return authenticationService.refreshAccessToken(request.refreshToken)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")
    }

    private fun String.mapToTokenResponse(): TokenResponse =
        TokenResponse(
            accessToken = this
        )
}

