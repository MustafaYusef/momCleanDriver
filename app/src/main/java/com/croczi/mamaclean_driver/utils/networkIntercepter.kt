package com.mustafayusef.holidaymaster.networks

import android.content.Context
import android.net.ConnectivityManager
import com.mustafayusef.holidaymaster.utils.noInternetExeption
import com.mustafayusef.holidaymaster.utils.toast

import okhttp3.Interceptor
import okhttp3.Response

class networkIntercepter(context: Context):Interceptor {
    val appcontext=context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isNetworkAvilable()){
         throw noInternetExeption("ther is no internet connection")
        }
        return chain.proceed(chain.request())
//            .newBuilder()
//            .addHeader("Connection", "close")
//            .addHeader("Transfer-Encoding", "chunked")
//            .build())
    }
    fun isNetworkAvilable():Boolean{
        val connectivityManager = appcontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            connectivityManager.activeNetworkInfo.also {
                return it != null && it.isConnected

            }
        } catch (e:NullPointerException){
            e.message?.let { appcontext.toast(it) }
            return false
        }
    }
}