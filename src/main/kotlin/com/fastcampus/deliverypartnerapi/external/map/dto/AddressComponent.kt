package com.fastcampus.deliverypartnerapi.external.map.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AddressComponent(
    @JsonProperty("long_name")
    val longName: String,
    @JsonProperty("short_name")
    val shortName: String,
    @JsonProperty("types")
    val types: List<String>
)