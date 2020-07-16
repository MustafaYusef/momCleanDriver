package com.croczi.mamaclean.data.auth.verification

data class verficationRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)