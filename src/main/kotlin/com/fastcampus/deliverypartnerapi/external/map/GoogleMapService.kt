package com.fastcampus.deliverypartnerapi.external.map

import com.fastcampus.deliverypartnerapi.external.map.dto.GeoCodeResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets


@Service
class GoogleMapService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
) {
    @Value("\${google.map.url}")
    private lateinit var googleMapUrl: String

    @Value("\${google.map.key}")
    private lateinit var googleMapKey: String

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    fun getGeoCodeByLocation(lat: String, lng: String): GeoCodeResponse {
        logger.info { ">>> 구글 GeoCode 정보 요청, location: $lat,$lng"}
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("latlng", "$lat,$lng")
        queryParams.add("key", googleMapKey)
        queryParams.add("language", "ko")

        val urlBuilder = UriComponentsBuilder.fromHttpUrl(googleMapUrl)
        urlBuilder.queryParams(queryParams)

        val headers = HttpHeaders()
        headers.contentType = MediaType("application", "json", StandardCharsets.UTF_8)

        val entity = HttpEntity<String>(headers)

        val responseEntity = restTemplate.exchange<String>(
            urlBuilder.build().toUri(), HttpMethod.GET, entity,
            String::class.java
        )

        logger.info { ">>> 구글 응답 : ${responseEntity.body}" }

        return objectMapper.readValue(responseEntity.body, GeoCodeResponse::class.java)
    }

    fun getGeocodeByAddress(address: String): GeoCodeResponse {
        logger.info { ">>> 구글 GeoCode 정보 요청, address: $address"}
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("address", address)
        queryParams.add("key", googleMapKey)
        queryParams.add("language", "ko")

        val urlBuilder = UriComponentsBuilder.fromHttpUrl(googleMapUrl)
        urlBuilder.queryParams(queryParams)

        val headers = HttpHeaders()
        headers.contentType = MediaType("application", "json", StandardCharsets.UTF_8)

        val entity = HttpEntity<String>(headers)

        val responseEntity = restTemplate.exchange<String>(
            urlBuilder.build().toUri(), HttpMethod.GET, entity,
            String::class.java
        )

        logger.info { ">>> 구글 응답 : ${responseEntity.body}" }

        return objectMapper.readValue(responseEntity.body, GeoCodeResponse::class.java)
    }
}