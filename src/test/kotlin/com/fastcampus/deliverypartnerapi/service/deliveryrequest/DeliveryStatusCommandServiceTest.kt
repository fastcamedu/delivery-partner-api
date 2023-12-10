package com.fastcampus.deliverypartnerapi.service.deliveryrequest

import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequest
import com.fastcampus.deliverypartnerapi.repository.deliveryrequest.DeliveryRequestRepository
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.set
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
class DeliveryStatusCommandServiceTest {

    @Mock
    private lateinit var repository: DeliveryRequestRepository

    @InjectMocks
    private lateinit var deliveryStatusCommandService: DeliveryStatusCommandService

    @DisplayName("배달 상태가 'READY' 일 때는 deliveryStart 호출하면 정상 처리된다")
    @Test
    fun deliveryStatusIsReady() {
        // given
        val deliveryRequestId = 1L
        val deliveryPartnerId = 2L
        val fixtureMonkey = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val deliveryRequest = fixtureMonkey.giveMeBuilder<DeliveryRequest>()
            .set(DeliveryRequest::deliveryRequestId, deliveryRequestId)
            .sample()

        // when
        `when`(repository.findById(deliveryRequestId)).thenReturn(
            Optional.of(deliveryRequest)
        )
        `when`(repository.save(deliveryRequest)).thenReturn(
            deliveryRequest
        )

        // when
        assertDoesNotThrow {
            deliveryStatusCommandService.deliveryStart(deliveryRequestId, deliveryPartnerId)
        }
    }
}