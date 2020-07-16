package com.mustafayusef.holidaymaster.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message:String){
    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
}
fun View.snackbar(message:String){
    Snackbar.make(this,message,Snackbar.LENGTH_LONG).also {snackbar->
        snackbar.setAction("OK"){
           snackbar.dismiss()
        }
    }.show()

}
fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int { // For example columnWidthdp=180
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt()
}
fun isAvailable(context: Context):Boolean{
    var avilable= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
    if(avilable== ConnectionResult.SUCCESS){
        return true
        // user can show map
    }else if(GoogleApiAvailability.getInstance().isUserResolvableError(avilable)){
        var dilog= GoogleApiAvailability.getInstance().getErrorDialog(context as Activity?,avilable,9001)
        dilog.show()
    }else{
        context?.toast("cant show the map")
    }
    return false
}