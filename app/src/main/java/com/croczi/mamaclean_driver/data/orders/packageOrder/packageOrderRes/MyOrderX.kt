package com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes

import java.io.Serializable

data class MyOrderX(
    val id: Int,
    val itemsCount: Int,
    val latitude: String,
    val longitude: String,
    val rating: Int,
    val status: String,
    val user: User
) : Serializable