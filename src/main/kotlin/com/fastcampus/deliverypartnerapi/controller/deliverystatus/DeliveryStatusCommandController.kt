package com.fastcampus.deliverypartnerapi.controller.deliverystatus

import com.fastcampus.deliverypartnerapi.controller.deliverystatus.dto.DeliveryStatusCommand
import com.fastcampus.deliverypartnerapi.controller.deliverystatus.dto.DeliveryStatusResponse
import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import com.fastcampus.deliverypartnerapi.service.deliveryrequest.DeliveryStatusCommandService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "DeliveryStatusCommandController", description = "배달 상태를 처리하는 API 모음")
@CrossOrigin("*")
@RestController
class DeliveryStatusCommandController(
    private val commandService: DeliveryStatusCommandService
) {

    companion object {
        private val logger = KotlinLogging.logger {  }
    }

    @PutMapping("/apis/deliveries/status")
    @Operation(
        summary = "배달의 진행 상태를 처리하는 API", description = "배달파트너에 의해 배달 진행 상태를 처리하는 API"
    )
    fun command(
        @RequestBody command: DeliveryStatusCommand
    ): ResponseEntity<DeliveryStatusResponse> {

        logger.info { ">>> 배달 진행 상태 변경 요청" }
        val deliveryRequest = when (command.deliveryStatus) {
            DeliveryStatus.DELIVERY_START -> commandService.deliveryStart(
                command.deliveryRequestId,
                deliveryPartnerId = command.deliveryPartnerId
            )

            DeliveryStatus.ARRIVAL_AT_STORE -> commandService.arrivalAtStore(
                command.deliveryRequestId,
                deliveryPartnerId = command.deliveryPartnerId
            )

            DeliveryStatus.PICKUP_COMPLETE -> commandService.pickupComplete(
                command.deliveryRequestId,
                deliveryPartnerId = command.deliveryPartnerId
            )

            DeliveryStatus.DELIVERY_COMPLETE -> commandService.deliveryComplete(
                command.deliveryRequestId,
                deliveryPartnerId = command.deliveryPartnerId
            )
            else -> {
                error("알 수 없는 배달 진행 상태입니다. $command")
            }
        }

        return ResponseEntity.ok(
            DeliveryStatusResponse(
                deliveryRequestId = command.deliveryRequestId,
                deliveryPartnerId = command.deliveryPartnerId,
                deliveryFee = deliveryRequest.deliveryFee,
                orderShortenId = deliveryRequest.orderShortenId,
                changedDeliveryStatus = deliveryRequest.deliveryStatus,
            )
        )
    }
}