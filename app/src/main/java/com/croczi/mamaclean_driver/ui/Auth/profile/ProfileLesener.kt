package com.mustafayusef.wakely.ui.auth

import com.croczi.mamaclean.data.auth.changePasswordRes
import com.croczi.mamaclean.data.auth.profile.myPackage.myPackages
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean_driver.data.auth.profile.profile.profileRes


interface ProfileLesener {
    fun OnStart()
    fun onFailer(message:String)
    fun onFailerNet(message:String)

    fun OnSuccessProfile(message: profileRes)
    fun OnSuccessChangeStatus(message: reSendRes)

    fun OnSuccessUpdatePhoto(message: reSendRes)

    fun OnSuccessChangePass(message: changePasswordRes)


}