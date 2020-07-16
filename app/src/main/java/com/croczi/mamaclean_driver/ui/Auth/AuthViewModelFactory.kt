package com.croczi.mamaclean.ui.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.wakely.ui.auth.AuthRepostary


class AuthViewModelFactory(val repostary: AuthRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repostary) as T
    }
}