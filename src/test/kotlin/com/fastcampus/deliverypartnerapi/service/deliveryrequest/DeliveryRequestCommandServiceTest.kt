package com.fastcampus.deliverypartnerapi.service.deliveryrequest

import com.fastcampus.deliverypartnerapi.domain.deliveryrequest.DeliveryStatus
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequest
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequestRepository
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*


@ExtendWith(MockitoExtension::class)
class DeliveryRequestCommandServiceTest {

    @Mock
    private lateinit var repository: DeliveryRequestRepository

    @InjectMocks
    private lateinit var deliveryRequestCommandService: DeliveryRequestCommandService


    @DisplayName("배달 요청 상태가 'READY'일 때는 accept 호출하면 정상 처리된다")
    @Test
    fun accept() {
        // given
        val deliveryRequestId = 1L
        val deliveryPartnerId = 2L
        val fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build()

        Mockito.`when`(repository.findById(deliveryRequestId)).thenReturn(
            Optional.of(createDeliveryRequest(deliveryRequestId))
        )

        // when
        assertDoesNotThrow {
            deliveryRequestCommandService.accept(deliveryRequestId, deliveryPartnerId)
        }
    }

    private fun createDeliveryRequest(deliveryRequestId: Long): DeliveryRequest {
        return DeliveryRequest(
            deliveryRequestId = deliveryRequestId,
            orderId = 1L,
            storeId = 2L,
            customerId = 3L,
            storeAddress = "호평동",
            deliveryPartnerId = 5L,
            deliveryAddress = "호평동",
            deliveryStatus = DeliveryStatus.READY,
            deliveryFee = BigDecimal(3_000),
            orderShortenId = "XXDDSSS",
        )
    }
}