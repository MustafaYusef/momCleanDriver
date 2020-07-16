package com.croczi.mamaclean_driver.network


import com.croczi.mamaclean.data.auth.changePasswordRes
import com.croczi.mamaclean.data.auth.signUp.signUpResponse
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.data.auth.verification.verficationRes
import com.croczi.mamaclean_driver.data.auth.profile.profile.profileRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails.packageOrderDetailsRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes.packageOrdersRes
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface myApi {



    @FormUrlEncoded
    @POST("drivers/login")
    suspend fun LogIn(
        @Field("phone") phone:String,
        @Field("password") passward:String,
        @Field("player_id") player_id:String
    ):Response<signUpResponse>

    @FormUrlEncoded
    @POST("drivers/verification")
    suspend fun verficationUser(@Field("code") code:String,
                                @Field("phone") phone:String
    ):Response<verficationRes>

    @GET("drivers/profile")
    suspend fun getProfile(
        @Header("token") token:String
    ):Response<profileRes>

    @FormUrlEncoded
    @PUT("drivers/changepassword")
    suspend fun changePassword(
        @Header("token") token:String,@Field("password")passward: String
        ,@Field("newpassword")new:String
    ):Response<changePasswordRes>


    @PUT("drivers/online")
    suspend fun changeOnlineStatus(
        @Header("token") token:String
    ):Response<reSendRes>



    @Multipart
    @PUT("drivers/profile")
    suspend fun updateProfilePhoto(
        @Header("token") token:String,
        @Part image: MultipartBody.Part
    ):Response<reSendRes>

    @FormUrlEncoded
    @POST("drivers/resetpassword")
    suspend fun reSendCode(@Field("phone") phone:String
    ):Response<reSendRes>

    @FormUrlEncoded
    @PUT("drivers/resetpassword")
    suspend fun reSetPassword(@Field("password") Password:String,
                              @Field("phone") phone:String,
                              @Field("reset_Code") reset_Code:String
    ):Response<reSendRes>



    @GET("drivers/orders?")
    suspend fun getMyOrder(@Header("token") token: String,
                           @Query("take") s:Int,@Query("skip") p:Int,
                           @Query("status") status:String
    ):Response<ordersNormalRes>
    //1- default 2-busy 3-received 4-delivered 5-cancelled

    @GET("orders/{id}")
    suspend fun getMyOrderDetails(@Header("Authorization") token: String,
                                  @Path("id") id:Int
    ):Response<ordersDetailsNormalRes>


    @FormUrlEncoded
    @PUT("drivers/orders/edit/{id}")
    suspend fun editeOrderStatus(@Header("token") token: String,
                                  @Path("id") id:Int,
                                 @Field("status") status:String
    ):Response<reSendRes>
    // 1- default 2-busy 3-received

    @PUT("drivers/orders/deliver/{id}")
    suspend fun deleverOrderToOffice(@Header("token") token: String,
                                 @Path("id") id:Int
    ):Response<reSendRes>


    // package Order

    @GET("drivers/package?")
    suspend fun getMyOrderPackage(@Header("token") token: String,
                           @Query("take") s:Int,@Query("skip") p:Int,
                           @Query("status") status:String
    ):Response<packageOrdersRes>
    //1- default 2-busy 3-received 4-delivered 5-cancelled

    @GET("package-orders/{id}")
    suspend fun getMyOrderPackageDetails(@Header("Authorization") token: String,
                                  @Path("id") id:Int
    ):Response<packageOrderDetailsRes>


    @FormUrlEncoded
    @PUT("drivers/package/edit/{id}")
    suspend fun editeOrderPackageStatus(@Header("token") token: String,
                                 @Path("id") id:Int,
                                 @Field("status") status:String
    ):Response<reSendRes>
    // 1- default 2-busy 3-received

    @PUT("drivers/package/deliver/{id}")
    suspend fun deleverOrderPackageToOffice(@Header("token") token: String,
                                     @Path("id") id:Int
    ):Response<reSendRes>
    companion object{

        operator fun invoke(
            networkIntercepter: networkIntercepter
        ):myApi{
            val spec = ConnectionSpec.Builder ( ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                )
                .build()
            val spec1 = ConnectionSpec.Builder ( ConnectionSpec.CLEARTEXT)
                .build()

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(networkIntercepter).addInterceptor(loggingInterceptor)
                .connectionSpecs(Collections.singletonList(spec))
//                .connectionSpecs(Collections.singletonList(spec1))
                .build()
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.maamclean.com/")
               // .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(myApi::class.java)
        }
    }
}
