package com.fastcampus.deliverypartnerapi.external.map.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.jsonwebtoken.lang.Strings

class GeoCodeResponse(
    @JsonProperty("results")
    val results: List<GeoCodeResult>,
) {
    fun getLongName(): String {
        if (results.isEmpty()) {
            return Strings.EMPTY
        }

        if (results.first().addressComponents.size == 1) {
            return results.first().addressComponents.first().longName
        }

        return results.first().addressComponents[1].longName
    }

    fun getShortName(): String {
        if (results.isEmpty()) {
            return Strings.EMPTY
        }

        if (results.first().addressComponents.size == 1) {
            return results.first().addressComponents.first().longName
        }

        return results.first().addressComponents[1].shortName
    }
}