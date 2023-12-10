package com.fastcampus.deliverypartnerapi.controller.deliverypartner

import com.fastcampus.deliverypartnerapi.controller.deliverypartner.dto.DeliveryPartnerDTO
import com.fastcampus.deliverypartnerapi.exception.NotFoundException
import com.fastcampus.deliverypartnerapi.service.delliverypartner.DeliveryPartnerService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "WalletController", description = "배달파트너 API")
@RestController
class DeliveryPartnerController(
    private val deliveryPartnerService: DeliveryPartnerService,
) {
    @GetMapping("/apis/delivery-partners/{deliveryPartnerId}")
    fun findById(@PathVariable deliveryPartnerId: Long): ResponseEntity<DeliveryPartnerDTO> {
        val partnerOptional = deliveryPartnerService.findById(deliveryPartnerId)
        if (partnerOptional.isEmpty) {
            throw NotFoundException("배달 파트너 정보를 찾을 수 없습니다. $deliveryPartnerId")
        }
        val deliveryPartner = partnerOptional.get()
        return ResponseEntity.ok(DeliveryPartnerDTO.of(deliveryPartner))
    }
}