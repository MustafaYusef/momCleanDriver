package com.mustafayusef.wakely.ui.auth

import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails.packageOrderDetailsRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes.packageOrdersRes


interface OrderLesener {
    fun OnStart()

    fun onFailer(message:String)
    fun onFailerNet(message:String)
     fun onSussessGetNormalOrder(it1: ordersNormalRes)
     fun onSussessGetNormalOrderDetails(it1: ordersDetailsNormalRes)
     fun onSussessChangeNormalOrder(it1: reSendRes)
     fun onSussessGetPackageOrder(it1: packageOrdersRes)
     fun onSussessGetPackageOrderDetails(it1: packageOrderDetailsRes)
     fun onSussessChangePackageOrder(it1: reSendRes)
     fun onSussessDeleverNormalOrder(it1: reSendRes)
     fun onSussessDeleverPackageOrder(it1: reSendRes)


}