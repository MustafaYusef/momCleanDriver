package com.croczi.mamaclean_driver.ui.main

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog


import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.ui.orders.OrderViewModel
import com.croczi.mamaclean.ui.orders.OrderViewModelFactory
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails.packageOrderDetailsRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes.packageOrdersRes
import com.croczi.mamaclean_driver.network.myApi


import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.OrderLesener
import com.mustafayusef.wakely.ui.auth.OrderRepostary
import kotlinx.android.synthetic.main.cart_fragment.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.pop_dilog.view.*


class mapFragment : Fragment(),OnMapReadyCallback, PermissionListener, OrderLesener {




   val Fine_LOCAION=android.Manifest.permission.ACCESS_FINE_LOCATION
    val Cursu_Location=android.Manifest.permission.ACCESS_COARSE_LOCATION
    val Location_perrmision_Code=1234

    var ifLocationPerrmissionGranted=false
    var mMap:GoogleMap?=null



    var ERROR_DAILOG=9001
    override fun OnStart() {
        progMap?.visibility=View.VISIBLE
    }

    override fun onFailer(message: String) {
       // context?.toast(message)
        progMap?.visibility=View.GONE
        if(message.toInt()==403){
            view?.findNavController()?.navigate(R.id.login)
            context?.toast("الحساب غير صالح")
        }
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
        progMap?.visibility=View.GONE
    }
var order:ordersNormalRes?=null
    override fun onSussessGetNormalOrder(it1: ordersNormalRes) {
        order=it1
        for(i in it1.data.orders) {
            mMap!!.addMarker(MarkerOptions().position(
                LatLng(
                i.latitude.toDouble(),i.longitude.toDouble()
            )
            ).title(i.user.name+","+i.user.phone).snippet(i.id.toString()+" true"))
        }
        progMap?.visibility=View.GONE
        viewModel?.getPackageOrder(MainActivity.cacheObj.token,25,0,"default")

    }
    override fun onSussessGetPackageOrder(it1: packageOrdersRes) {
        var markerBitmap = BitmapFactory.decodeResource(getResources()
            ,R.drawable.ic_placeholder_2);

        for(i in it1.data.myOrder.myOrder) {
            mMap!!.addMarker(MarkerOptions().
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .position(
                LatLng(
                    i.latitude.toDouble(),i.longitude.toDouble()
                )
            ).title(i.user.name+","+i.user.phone).snippet(i.id.toString()+" false"))
        }
        progMap?.visibility=View.GONE
        mMap?.setOnInfoWindowClickListener{
            var bundel=Bundle()
            if(!it.snippet.isNullOrEmpty()){
                if (it.snippet.split(" ").get(1)=="true"){
                    bundel.putBoolean("flage",true)
                    bundel.putInt("id",it.snippet.split(" ").get(0) .toInt())
                }else{
                    bundel.putBoolean("flage",false)
                    bundel.putInt("id",it.snippet.split(" ").get(0) .toInt())
                }
                bundel.putString("name",it.title.split(",").get(0))
                bundel.putString("phone",it.title.split(",").get(1))
                bundel.putDouble("lat",it.position.latitude)
                bundel.putDouble("lon",it.position.longitude)
                view?.findNavController()?.navigate(R.id.orderDetailsFragment,bundel)
            }

        }

    }
    override fun onSussessGetNormalOrderDetails(it1: ordersDetailsNormalRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessChangeNormalOrder(it1: reSendRes) {

    }



    override fun onSussessGetPackageOrderDetails(it1: packageOrderDetailsRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessChangePackageOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessDeleverNormalOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessDeleverPackageOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
    private lateinit var viewModel: OrderViewModel
    var LocationPass:android.location.Location?=null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



            Dexter.withActivity(this.requireActivity()).withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(this).check()

            val networkIntercepter= context?.let { networkIntercepter(it) }
            val api= networkIntercepter?.let { myApi(it) }

            val repostary= OrderRepostary(api!!)
            val factory= OrderViewModelFactory(repostary)

            viewModel = ViewModelProviders.of(this,factory).get(OrderViewModel::class.java)
            viewModel?.Auth=this
            ordersList?.layoutManager= LinearLayoutManager(context!!)

        changeState?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.profileFragment2)
        }
        if(!MainActivity.cacheObj.state){
            changeState?.visibility=View.VISIBLE
        }

//            LocationPass.let {
//                viewModel?.getNormalOrder(MainActivity.cacheObj.token,10,0,"default")
//
//            }

//            mMap?.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener() {
//                 context?.toast("click")
//                  fun onInfoWindowClick(marker: Marker) {
//                    var bundel=Bundle()
//                      if (marker.snippet.split(" ").get(1)=="true"){
//                          bundel.putBoolean("flage",true)
//                          bundel.putInt("id",marker.snippet.toInt())
//                      }else{
//                          bundel.putBoolean("flage",false)
//                          bundel.putInt("id",marker.snippet.toInt())
//                      }
//                    view?.findNavController()?.navigate(R.id.orderDetailsFragment,bundel)
//                }
//            });


        //getLocationPermmission()



    }

        // Inflate the layout for this fragment
var DEFAUL_ZOOM=10f
    var locationCall:LocationCallback?=null
    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        initMap()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token!!.continuePermissionRequest()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        if(response!!.isPermanentlyDenied){
            var dilog=AlertDialog.Builder(context!!)
            dilog.setTitle("Perrmission denid")
                .setMessage("perrmission is permanintly denid go to the setting")
                .setNegativeButton("cancel",null)
                .setPositiveButton("Ok") { dialogInterface, i ->
                    var intent=Intent()
                    intent.action= Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data= Uri.fromParts("package",context!!. packageName,null)
                }
            dilog.show()
        }else{
            context?.toast("Perrmisions is Denid")
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        //context?.toast("On map ready")
        mMap=p0
        fusedLocation=LocationServices.getFusedLocationProviderClient(this.requireActivity())
//        if(ifLocationPerrmissionGranted){
//          //  getDiviceLocation()
//
//            if(ContextCompat.checkSelfPermission(this,Fine_LOCAION)
//                !=PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this,Cursu_Location)
//                !=PackageManager.PERMISSION_GRANTED){
//                  return
//                }
        mMap!!.isMyLocationEnabled=true
        mMap!!.uiSettings.isMyLocationButtonEnabled=true
        mMap!!.uiSettings.isZoomControlsEnabled=true

        // check if GPS is enable or not
        var locationRequest=LocationRequest.create()
        locationRequest.interval=10000
        locationRequest.fastestInterval=5000
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY

        var builder=LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        var settingLocation=LocationServices.getSettingsClient(this.requireActivity())
        var task=settingLocation.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this.requireActivity(), OnSuccessListener {
            getDiviceLocation()
        })

        task.addOnFailureListener(this.requireActivity(), OnFailureListener {
            if(it is ResolvableApiException){
                var resolv=it as ResolvableApiException
                try{
                    resolv.startResolutionForResult(this.requireActivity(),51)
                }catch (e:IntentSender.SendIntentException){
                    e.printStackTrace()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==51){
            if(resultCode== Activity.RESULT_OK){
                getDiviceLocation()
            }

        }
    }



    fun moveCamera(latLng: LatLng,zoom:Float){
       // context?.toast("moveCamera ")
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom))
        mMap!!.addMarker(MarkerOptions().position(latLng).title("موقعك الحالي"))
    }
    var fusedLocation:FusedLocationProviderClient?=null
    fun getDiviceLocation(){
    //    context?.toast("getDiviceLocation ")
        var currentLocation:android.location.Location?=null
        try {
            fusedLocation!!.lastLocation!!.addOnCompleteListener {
                if (it.isSuccessful) {
                   // context?.toast("find location successfully")
                    currentLocation = it.result
                    if (currentLocation != null) {
                        moveCamera(
                            LatLng(
                                currentLocation!!.latitude,
                                currentLocation!!.longitude
                            ), DEFAUL_ZOOM
                        )
                        LocationPass=currentLocation
                        viewModel?.getNormalOrder(MainActivity.cacheObj.token,25,0,"default")

                    }else{

                        var locationRequest = LocationRequest.create()
                        locationRequest.interval = 10000
                        locationRequest.fastestInterval = 5000
                        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        locationCall= object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                super.onLocationResult(locationResult)
                                if (locationResult == null) {
                                    return
                                } else {
                                    currentLocation = locationResult.lastLocation
                                    moveCamera(LatLng(currentLocation!!.latitude, currentLocation!!.longitude), DEFAUL_ZOOM)
                                    fusedLocation!!.removeLocationUpdates(locationCall)
                                }
                            }
                        }
                        fusedLocation!!.requestLocationUpdates(locationRequest, locationCall, null)

                    }
                } else {
                    context?.toast("unable to find current location")
                }

            }

        }catch (e:SecurityException){
            context?.toast("security exeption")
        }
    }



    fun initMap(){
      //  context?.toast("inisiate map")
        val mapFragment= childFragmentManager!!.findFragmentById(R.id.mapLoc) as SupportMapFragment

        mapFragment.getMapAsync(this)

//            location = Location(activity as AppCompatActivity, object :locationListener{
//                override fun locationResponse(locationResult: LocationResult) {
//                    mMap?.clear()
//                    val sydney = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
//                    mMap?.addMarker(MarkerOptions().position(sydney).title("Hi").snippet("Let's go!"))
//                    mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f))
//                }
//            })

    }
  fun getLocationPermmission(){
      var perrmissions= arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
          android.Manifest.permission.ACCESS_COARSE_LOCATION)

      if(ContextCompat.checkSelfPermission(context!!.applicationContext,Fine_LOCAION)
      ==PackageManager.PERMISSION_GRANTED){
          if(ContextCompat.checkSelfPermission(context!!.applicationContext,Cursu_Location)
              ==PackageManager.PERMISSION_GRANTED){
              ifLocationPerrmissionGranted=true
              initMap()
          }
          else{
              ActivityCompat.requestPermissions(this.requireActivity(),perrmissions,Location_perrmision_Code)
          }
      }else{
          ActivityCompat.requestPermissions(this.requireActivity(),perrmissions,Location_perrmision_Code)
      }
  }

    @Override
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        ifLocationPerrmissionGranted=false
        when(requestCode){
            Location_perrmision_Code->{
                if(grantResults.size>0){
                    for(i in 0 until  grantResults.size){
                        if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                            ifLocationPerrmissionGranted=false
                            return
                        }
                    }
                    ifLocationPerrmissionGranted=true
                    //inisialize map
                 initMap()

                }
            }
        }
    }




//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        location?.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//    override fun onStart() {
//        super.onStart()
//        location?.inicializeLocation()
//    }
//    override fun onPause() {
//        super.onPause()
//        location?.stopUpdateLocation()
//    }
}


