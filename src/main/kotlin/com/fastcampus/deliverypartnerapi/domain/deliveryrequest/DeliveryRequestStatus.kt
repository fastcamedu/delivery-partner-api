package com.fastcampus.deliverypartnerapi.domain.deliveryrequest

enum class DeliveryRequestStatus(description: String) {
    ACCEPT("수락"),
    REJECT("거절"),
    CANCEL("취소")
}