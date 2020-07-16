package com.croczi.mamaclean_driver.data.auth.profile.profile

data class profileRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)