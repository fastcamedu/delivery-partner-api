package com.fastcampus.deliverypartnerapi.external.map.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GeoCodeResult(
    @JsonProperty("address_components")
    val addressComponents: List<AddressComponent>,
    @JsonProperty("formatted_address")
    val formattedAddress: String,
    @JsonProperty("types")
    val types: List<String>,
    @JsonProperty("place_id")
    val placeId: String,
    @JsonProperty("geometry")
    val geometry: Geometry,
)
