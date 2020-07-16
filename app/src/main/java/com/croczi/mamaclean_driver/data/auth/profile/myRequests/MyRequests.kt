package com.croczi.mamaclean.data.auth.profile.myRequests

data class MyRequests(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)