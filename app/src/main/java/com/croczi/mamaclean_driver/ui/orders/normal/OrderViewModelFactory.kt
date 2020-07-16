package com.croczi.mamaclean.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.wakely.ui.auth.OrderRepostary


class OrderViewModelFactory(val repostary: OrderRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(repostary) as T
    }
}