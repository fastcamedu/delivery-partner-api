package com.fastcampus.deliverypartnerapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "배달파트너 API",
        description = "배달파트너를 위한 API 목록",
        version = "0.1"
    )
)
@Configuration
class SwaggerConfig