package com.croczi.mamaclean.data.auth.profile.myRequests

data class Package(
    val createAt: String,
    val currency: String,
    val days: Int,
    val description_ar: String,
    val description_en: String,
    val `file`: String,
    val id: Int,
    val name_ar: String,
    val name_en: String,
    val officeId: Int,
    val price: Int,
    val softDelele: Boolean,
    val visits_count: Int
)