package com.mustafayusef.wakely.ui.auth


import com.croczi.mamaclean.data.auth.profile.myPackage.myPackages
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails.packageOrderDetailsRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes.packageOrdersRes
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.wakely.utils.SafeApiRequest

class OrderRepostary(val api: myApi): SafeApiRequest() {

/// ordere
    suspend fun getNormalOrder(token:String,s:Int,p:Int,status:String): ordersNormalRes {
        return SafeRequest {
            api.getMyOrder(token,s,p,status)
        }
    }
    suspend fun getCategoryOrderDetails(token:String,id:Int): ordersDetailsNormalRes {
        return SafeRequest {
            api.getMyOrderDetails(token,id)
        }
    }
    suspend fun changeStatusOrder(token:String,id:Int,status: String): reSendRes {
        return SafeRequest {
            api.editeOrderStatus(token,id,status)
        }
    }

    suspend fun deleverOrderToOffice(token:String,id:Int): reSendRes {
        return SafeRequest {
            api.deleverOrderToOffice(token,id)
        }
    }

/// package
    suspend fun getPackageOrder(token:String,s:Int,p:Int,status:String): packageOrdersRes {
        return SafeRequest {
            api.getMyOrderPackage(token,s,p,status)
        }
    }
    suspend fun getPackageOrderDetails(token:String,id:Int): packageOrderDetailsRes {
        return SafeRequest {
            api.getMyOrderPackageDetails(token,id)
        }
    }
    suspend fun changeStatusOrderPackage(token:String,id:Int,status: String): reSendRes {
        return SafeRequest {
            api.editeOrderPackageStatus(token,id,status)
        }
    }
    suspend fun deleverOrderPackageToOffice(token:String,id:Int): reSendRes {
        return SafeRequest {
            api.deleverOrderPackageToOffice(token,id)
        }
    }

}


