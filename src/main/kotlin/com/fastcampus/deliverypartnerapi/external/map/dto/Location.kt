package com.fastcampus.deliverypartnerapi.external.map.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Location(
    @JsonProperty("lat")
    val lat: String,
    @JsonProperty("lng")
    val lng: String,
)
