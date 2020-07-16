package com.croczi.mamaclean.data.auth.verification.reSendCode

data class reSendRes(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)
