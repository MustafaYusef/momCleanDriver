package com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes

data class ordersNormalRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)