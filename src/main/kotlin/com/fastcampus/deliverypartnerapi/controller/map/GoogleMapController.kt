package com.fastcampus.deliverypartnerapi.controller.map

import com.fastcampus.deliverypartnerapi.external.map.GoogleMapService
import com.fastcampus.deliverypartnerapi.external.map.dto.GeoCodeResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
class GoogleMapController(
    private val googleMapService: GoogleMapService,
) {
    @GetMapping("/apis/geocode/location")
    fun getGeocodeByLocation(@RequestParam lat: String, @RequestParam lng: String): GeoCodeResponse {
        return googleMapService.getGeoCodeByLocation(lat = lat, lng = lng)
    }

    @GetMapping("/apis/geocode/location/long-name")
    fun getGeocodeLongNameByLocation(@RequestParam lat: String, @RequestParam lng: String): String {
        val geoCodeResponse = googleMapService.getGeoCodeByLocation(lat = lat, lng = lng)
        return geoCodeResponse.getLongName()
    }

    @GetMapping("/apis/geocode/address")
    fun getGeocodeByAddress(@RequestParam address: String): GeoCodeResponse {
        return googleMapService.getGeocodeByAddress(address)
    }
}