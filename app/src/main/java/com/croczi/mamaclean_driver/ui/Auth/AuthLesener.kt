package com.mustafayusef.wakely.ui.auth

import com.croczi.mamaclean.data.auth.signUp.signUpResponse
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.data.auth.verification.verficationRes



interface AuthLesener {
    fun OnStart()
    fun OnSuccesSignIn(response:signUpResponse)
    fun onFailer(message:String)
    fun onFailerNet(message:String)




    fun OnSuccessSendCode(message: reSendRes)
     fun OnSuccessVerefication(it1: verficationRes)
     fun OnSuccessResetPassword(it1: reSendRes)
}