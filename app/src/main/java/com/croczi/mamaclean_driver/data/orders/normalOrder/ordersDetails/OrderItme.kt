package com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails

data class OrderItme(
    val count: Int,
    val id: Int,
    val item: Item,
    val type: String
)