package com.fastcampus.deliverypartnerapi.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "HelloController", description = "Hello API")
@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello Delivery Partner API"
    }
}