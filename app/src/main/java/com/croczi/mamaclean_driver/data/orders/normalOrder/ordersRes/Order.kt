package com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes

import java.io.Serializable

data class Order(
    val createAt: String,
    val id: Int,
    val itemsCount: Int,
    val latitude: String,
    val longitude: String,
    val officeId: Int,
    val price: Int,
    val rating: Int,
    val receivedAt: String,
    val status: String,
    val type: String,
    val user: User
) : Serializable