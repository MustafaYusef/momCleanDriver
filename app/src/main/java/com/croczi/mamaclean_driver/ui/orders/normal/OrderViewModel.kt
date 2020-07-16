package com.croczi.mamaclean.ui.orders

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.wakely.ui.auth.OrderLesener
import com.mustafayusef.wakely.ui.auth.OrderRepostary
import com.mustafayusef.wakely.utils.corurtins
import okhttp3.internal.http2.StreamResetException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

class OrderViewModel(val repostary: OrderRepostary) : ViewModel() {
    var Auth: OrderLesener?=null
    /// ordere
     fun getNormalOrder(token:String,s:Int,p:Int,status:String) {
        Auth?.OnStart()
        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.getNormalOrder(
                    token,s,p,status
                )
                CarsDetailsResponse ?.let {it1->

                    Auth?.onSussessGetNormalOrder(it1)
                }
            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerNet(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ConnectException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SSLHandshakeException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e: StreamResetException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e: NetworkErrorException){
                e.message?.let { Auth?.onFailerNet(it)}
            }
        }

    }
     fun getCategoryOrderDetails(token:String,id:Int) {
         Auth?.OnStart()
         corurtins.main {
             try {
                 val CarsDetailsResponse=repostary.getCategoryOrderDetails(
                     token,id)
                 CarsDetailsResponse ?.let {it1->

                     Auth?.onSussessGetNormalOrderDetails(it1)
                 }
             }catch (e: ApiExaptions){
                 e.message?.let { Auth?.onFailer(it) }

             }catch (e: noInternetExeption){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: SocketTimeoutException){
                 e.message?.let { Auth?.onFailerNet(it) }}
             catch (e: SocketException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: ProtocolException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: ConnectException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: SSLHandshakeException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }catch (e: StreamResetException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }catch (e:NetworkErrorException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }
         }
    }
     fun changeOrderStatus(token:String,id:Int,status: String) {
         Auth?.OnStart()
         corurtins.main {
             try {
                 val CarsDetailsResponse=repostary.changeStatusOrder(
                     token,id,status)
                 CarsDetailsResponse ?.let {it1->

                     Auth?.onSussessChangeNormalOrder(it1)
                 }
             }catch (e: ApiExaptions){
                 e.message?.let { Auth?.onFailer(it) }

             }catch (e: noInternetExeption){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: SocketTimeoutException){
                 e.message?.let { Auth?.onFailerNet(it) }}
             catch (e: SocketException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: ProtocolException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: ConnectException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: SSLHandshakeException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }catch (e: StreamResetException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }catch (e:NetworkErrorException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }
         }
    }
    fun deleverOrder(token:String,id:Int) {
        Auth?.OnStart()
        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.deleverOrderToOffice(
                    token,id)
                CarsDetailsResponse ?.let {it1->

                    Auth?.onSussessDeleverNormalOrder(it1)
                }
            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerNet(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ConnectException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SSLHandshakeException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e: StreamResetException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e:NetworkErrorException){
                e.message?.let { Auth?.onFailerNet(it)}
            }
        }
    }


    /// package
     fun getPackageOrder(token:String,s:Int,p:Int,status: String) {
        Auth?.OnStart()
        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.getPackageOrder(
                    token,s,p,status)
                CarsDetailsResponse ?.let {it1->

                    Auth?.onSussessGetPackageOrder(it1)
                }
            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerNet(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ConnectException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SSLHandshakeException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e: StreamResetException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e:NetworkErrorException){
                e.message?.let { Auth?.onFailerNet(it)}
            }
        }
    }
     fun getPackageOrderDetails(token:String,id:Int) {
         Auth?.OnStart()
         corurtins.main {
             try {
                 val CarsDetailsResponse=
                     repostary.getPackageOrderDetails(token,id)
                 CarsDetailsResponse ?.let {it1->

                     Auth?.onSussessGetPackageOrderDetails(it1)
                 }
             }catch (e: ApiExaptions){
                 e.message?.let { Auth?.onFailer(it) }

             }catch (e: noInternetExeption){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: SocketTimeoutException){
                 e.message?.let { Auth?.onFailerNet(it) }}
             catch (e: SocketException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: ProtocolException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: ConnectException){
                 e.message?.let { Auth?.onFailerNet(it) }
             }catch (e: SSLHandshakeException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }catch (e: StreamResetException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }catch (e:NetworkErrorException){
                 e.message?.let { Auth?.onFailerNet(it)}
             }
         }
    }
    fun changeOrderPackageStatus(token:String,id:Int,status: String) {
        Auth?.OnStart()
        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.changeStatusOrderPackage(
                    token,id,status)
                CarsDetailsResponse ?.let {it1->

                    Auth?.onSussessChangePackageOrder(it1)
                }
            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerNet(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ConnectException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SSLHandshakeException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e: StreamResetException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e:NetworkErrorException){
                e.message?.let { Auth?.onFailerNet(it)}
            }
        }
    }
    fun deleverPackageOrder(token:String,id:Int) {
        Auth?.OnStart()
        corurtins.main {
            try {
                val CarsDetailsResponse=repostary.deleverOrderPackageToOffice(
                    token,id)
                CarsDetailsResponse ?.let {it1->

                    Auth?.onSussessDeleverPackageOrder(it1)
                }
            }catch (e: ApiExaptions){
                e.message?.let { Auth?.onFailer(it) }

            }catch (e: noInternetExeption){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SocketTimeoutException){
                e.message?.let { Auth?.onFailerNet(it) }}
            catch (e: SocketException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ProtocolException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: ConnectException){
                e.message?.let { Auth?.onFailerNet(it) }
            }catch (e: SSLHandshakeException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e: StreamResetException){
                e.message?.let { Auth?.onFailerNet(it)}
            }catch (e:NetworkErrorException){
                e.message?.let { Auth?.onFailerNet(it)}
            }
        }
    }
}
