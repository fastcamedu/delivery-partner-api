package com.fastcampus.deliverypartnerapi.config

import com.fastcampus.deliverypartnerapi.repository.deliverypartner.DeliveryPartnerRepository
import com.fastcampus.deliverypartnerapi.security.JwtProperties
import com.fastcampus.deliverypartnerapi.service.delliverypartner.DeliveryPartnerService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class DeliveryPartnerApiConfig {

    @Bean
    fun userDetailsService(
        deliveryPartnerRepository: DeliveryPartnerRepository,
        passwordEncoder: PasswordEncoder,
    ): UserDetailsService =
        DeliveryPartnerService(deliveryPartnerRepository, passwordEncoder)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(
        deliveryPartnerRepository: DeliveryPartnerRepository,
        passwordEncoder: PasswordEncoder
    ): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(deliveryPartnerRepository, passwordEncoder))
                it.setPasswordEncoder(passwordEncoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}