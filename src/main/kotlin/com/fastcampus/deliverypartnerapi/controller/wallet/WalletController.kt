package com.fastcampus.deliverypartnerapi.controller.wallet

import com.fastcampus.deliverypartnerapi.controller.wallet.dto.WalletQueryResponse
import com.fastcampus.deliverypartnerapi.controller.wallet.dto.WithdrawalRequest
import com.fastcampus.deliverypartnerapi.controller.wallet.dto.WithdrawalResponse
import com.fastcampus.deliverypartnerapi.service.wallet.WalletCommandService
import com.fastcampus.deliverypartnerapi.service.wallet.WalletQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "WalletController", description = "배달파트너 지갑 API")
@RestController
class WalletController(
    private val walletQueryService: WalletQueryService,
    private val walletCommandService: WalletCommandService,
) {

    @GetMapping("/apis/delivery-partners/{deliveryPartnerId}/wallets")
    @Operation(
        summary = "배달파트너의 지갑 조회 API", description = "배달파트너에 지갑에 잔고를 조회하는 API, "
    )
    fun detail(@PathVariable("deliveryPartnerId") deliveryPartnerId: Long): ResponseEntity<WalletQueryResponse> {
        val deposit = walletQueryService.balance(deliveryPartnerId)
        return ResponseEntity.ok(
            WalletQueryResponse(
                deliveryPartnerId = deliveryPartnerId,
                balance = deposit,
            )
        )
    }

    @PostMapping("/apis/delivery-partners/{deliveryPartnerId}/wallets/withdrawal")
    @Operation(
        summary = "배달파트너의 인출 API", description = "배달파트너가 지갑에서 인출을 요청하는 API, "
    )
    fun withdrawal(@RequestBody withdrawalRequest: WithdrawalRequest): ResponseEntity<WithdrawalResponse> {
        val deposit = walletQueryService.balance(withdrawalRequest.deliveryPartnerId)
        if (withdrawalRequest.withdrawalAmount > deposit) {
            error("인출할 금액이 부족합니다.")
        }

        val deliveryPartnerWallet = walletCommandService.withdrawal(
            deliveryPartnerId = withdrawalRequest.deliveryPartnerId,
            withdrawalAmount = withdrawalRequest.withdrawalAmount
        )

        return ResponseEntity.ok(
            WithdrawalResponse(
                deliveryPartnerId = withdrawalRequest.deliveryPartnerId,
                originalBalance = deposit,
                withdrawalBalance = withdrawalRequest.withdrawalAmount,
                balance = deliveryPartnerWallet.deposit,
            )
        )
    }
}