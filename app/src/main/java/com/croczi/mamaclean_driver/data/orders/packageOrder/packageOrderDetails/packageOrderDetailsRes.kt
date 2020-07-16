package com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails

data class packageOrderDetailsRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)