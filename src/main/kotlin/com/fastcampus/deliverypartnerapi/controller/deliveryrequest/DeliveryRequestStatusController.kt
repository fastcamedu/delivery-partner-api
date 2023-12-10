package com.fastcampus.deliverypartnerapi.controller.deliveryrequest

import com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto.DeliveryRequestStatusRequest
import com.fastcampus.deliverypartnerapi.controller.deliveryrequest.dto.DeliveryRequestStatusResponse
import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryRequestStatus
import com.fastcampus.deliverypartnerapi.service.deliveryrequest.DeliveryRequestCommandService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "DeliveryRequestStatusController", description = "주문 배달 요청 API")
@CrossOrigin("*")
@RestController
class DeliveryRequestStatusController(
    private val commandService: DeliveryRequestCommandService,
) {
    companion object {
        private val logger = KotlinLogging.logger {  }
    }

    @PutMapping("/apis/delivery-requests/status")
    @Operation(
        summary = "배달 요청의 상태를 변경하는 API", description = "배달파트너에 의해 주문 배달 요청의 상태를 변경하는 API"
    )
    fun command(
        @RequestBody request: DeliveryRequestStatusRequest,
    ): ResponseEntity<DeliveryRequestStatusResponse> {
        logger.info { ">>> 배달 요청 상태 변경, $request" }

        when(request.deliveryRequestStatus) {
            DeliveryRequestStatus.ACCEPT -> commandService.accept(request.deliveryRequestId, request.deliveryPartnerId)
            DeliveryRequestStatus.REJECT -> commandService.reject(request.deliveryRequestId, request.deliveryPartnerId)
            DeliveryRequestStatus.CANCEL -> commandService.cancel(request.deliveryRequestId, request.deliveryPartnerId)
        }

        return ResponseEntity.ok(
            DeliveryRequestStatusResponse(
                orderDeliveryId = request.deliveryRequestId,
                deliveryPartnerId = request.deliveryPartnerId,
                changedDeliveryStatus = request.deliveryRequestStatus,
            )
        )
    }
}