package com.fastcampus.deliverypartnerapi.controller.deliveryrequest

import com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto.DeliveryRequestDTO
import com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto.DeliveryRequestResponse
import com.fastcampus.deliverypartnerapi.exception.NotFoundException
import com.fastcampus.deliverypartnerapi.service.deliveryrequest.DeliveryRequestQueryService
import com.fastcampus.deliverypartnerapi.service.delliverypartner.DeliveryPartnerService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "DeliveryRequestQueryController", description = "주문 배달 요청을 조회하는 API 모음")
@CrossOrigin("*")
@RestController
class DeliveryRequestQueryController(
    private val deliveryPartnerService: DeliveryPartnerService,
    private val deliveryRequestQueryService: DeliveryRequestQueryService,
) {
    companion object {
        private val logger = KotlinLogging.logger {  }
    }

    @Operation(
        summary = "배달 요청 목록 조회 API", description = "배달 요청 목록을 조회하는 API"
    )
    @GetMapping("/apis/delivery-requests")
    fun list(@RequestParam deliveryPartnerId: Long): ResponseEntity<DeliveryRequestResponse> {
        logger.info { ">>> 배달 요청 목록 조회, deliveryPartnerId: $deliveryPartnerId" }

        // 배달 파트너의 위치 조회
        val deliveryPartnerOptional = deliveryPartnerService.findById(deliveryPartnerId)
        if (deliveryPartnerOptional.isEmpty) {
            throw NotFoundException("배달 파트너 정보를 찾을 수 없습니다. $deliveryPartnerId")
        }
        val deliveryPartner = deliveryPartnerOptional.get()

        // 배달 파트너가 가능한 주문 조회
        val deliveryRequests = deliveryRequestQueryService.findAllByDeliveryAddressContains(deliveryPartner.deliveryZone)
        val deliveryRequestDTOS = deliveryRequests.map { DeliveryRequestDTO.of(it) }

        return ResponseEntity.ok(
            DeliveryRequestResponse(deliveryRequests =  deliveryRequestDTOS)
        )
    }

    @GetMapping("/apis/delivery-requests/{deliveryRequestId}")
    @Operation(
        summary = "배달 요청 상세 API", description = "배달 요청 상세 정보를 조회하는 API"
    )
    fun detail(@PathVariable("deliveryRequestId") deliveryRequestId: Long): ResponseEntity<DeliveryRequestDTO> {
        logger.info { ">>> 배달 요청 상세 조회, deliveryRequestId: $deliveryRequestId" }

        val requestOptional = deliveryRequestQueryService.findById(deliveryRequestId)
        if (requestOptional.isEmpty) {
            throw NotFoundException("배달 요청 상세 정보를 찾지 못했습니다. $deliveryRequestId")
        }

        val deliveryRequest = requestOptional.get()

        return ResponseEntity.ok(
            DeliveryRequestDTO(
                deliveryRequestId = deliveryRequest.deliveryRequestId,
                orderId = deliveryRequest.orderId,
                storeId = deliveryRequest.storeId,
                customerId = deliveryRequest.customerId,
                storeAddress = deliveryRequest.storeAddress,
                deliveryAddress = deliveryRequest.deliveryAddress,
                deliveryPartnerId = deliveryRequest.deliveryPartnerId,
                deliveryStatus = deliveryRequest.deliveryStatus,
                deliveryFee = deliveryRequest.deliveryFee,
            )
        )
    }
}