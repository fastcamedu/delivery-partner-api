package com.fastcampus.deliverypartnerapi.external.map.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PlusCode(
    @JsonProperty("compound_code")
    val compoundCode: String,
    @JsonProperty("global_code")
    val globalCode: String,
)
