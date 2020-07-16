package com.croczi.mamaclean.data.auth.profile.myRequests

data class MyRequest(
    val createAt: String,
    val id: Int,
    val `package`: Package,
    val status: String
)