package com.croczi.mamaclean.ui.Auth.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.wakely.ui.auth.AuthRepostary


class profileViewModelFactory(val repostary: AuthRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repostary) as T
    }
}