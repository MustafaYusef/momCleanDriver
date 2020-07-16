package com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails

data class Item(
    val categoryId: Int,
    val createAt: String,
    val currency: String,
    val dw_price: Int,
    val id: Int,
    val name_ar: String,
    val name_en: String,
    val officeId: Int,
    val photo: String,
    val softDelete: Boolean,
    val wash_price: Int
)