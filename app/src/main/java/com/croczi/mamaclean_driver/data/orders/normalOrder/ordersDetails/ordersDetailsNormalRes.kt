package com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails

data class ordersDetailsNormalRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)