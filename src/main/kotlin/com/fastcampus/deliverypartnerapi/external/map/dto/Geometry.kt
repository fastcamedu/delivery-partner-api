package com.fastcampus.deliverypartnerapi.external.map.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Geometry(
    @JsonProperty("location")
    val location: Location,
)
