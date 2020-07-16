package com.croczi.mamaclean_driver.data.auth.profile.profile

data class Profile(
    val createAt: String,
    val id: Int,
    val isActive: Boolean,
    val name: String,
    val online: Boolean,
    val phone: String,
    val photo: String,
    val reset_Code: String
)