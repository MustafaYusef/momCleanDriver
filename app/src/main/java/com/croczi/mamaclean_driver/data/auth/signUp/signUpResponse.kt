package com.croczi.mamaclean.data.auth.signUp

data class signUpResponse(
    val `data`: Data,
    val error: String,
    val message: String,
    val statusCode: Int
)
