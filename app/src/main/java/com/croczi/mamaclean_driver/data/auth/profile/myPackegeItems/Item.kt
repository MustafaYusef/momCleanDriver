package com.croczi.mamaclean.data.auth.profile.myPackegeItems

data class Item(
    val categoryId: Int,
    val createAt: String,
    val discont: Int,
    val dw_price: Int,
    val id: Int,
    val min_discont: Int,
    val name: String,
    val photo: String,
    val softDelete: Boolean,
    val wash_price: Int
)