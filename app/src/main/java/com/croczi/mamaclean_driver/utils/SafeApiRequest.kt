package com.mustafayusef.wakely.utils

import com.google.gson.JsonIOException
import com.google.gson.JsonObject
import com.mustafayusef.holidaymaster.utils.ApiExaptions
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONStringer
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T:Any> SafeRequest(call:suspend ()->Response<T> ):T{
        val response=call.invoke()
        if(response.isSuccessful){
            return response.body()!!

        }else{
            val error=response.errorBody()?.string()
            val message=StringBuilder()
            error?.let {
                try{
                 message.append( JSONObject(it).getString("message"))
                }catch (e:JSONException){ }
                message.append("\n")
            }
            message.append("error code ${response.code()}")
            throw ApiExaptions(response.code().toString())
             //response.errorBody().toString()
        }
    }
}