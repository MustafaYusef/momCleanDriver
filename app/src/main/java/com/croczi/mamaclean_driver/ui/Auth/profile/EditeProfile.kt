package com.croczi.mamaclean.ui.Auth.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.croczi.mamaclean.data.auth.changePasswordRes
import com.croczi.mamaclean.data.auth.profile.myPackage.myPackages
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.auth.profile.profile.profileRes
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.AuthRepostary
import com.mustafayusef.wakely.ui.auth.ProfileLesener
import kotlinx.android.synthetic.main.fragment_edite_profile.*
import kotlinx.android.synthetic.main.location_dailog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class EditeProfile : Fragment(),ProfileLesener {
    override fun OnSuccessProfile(message: profileRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnSuccessChangeStatus(message: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    override fun OnSuccessUpdatePhoto(message: reSendRes) {
       // context?.toast(message.message+"  photo update")

        EditeProfileBtn?.visibility=View.VISIBLE
        ProgEditProfile?.visibility=View.GONE
        view?.findNavController()?.navigate(R.id.profileFragment2)
    }

    override fun OnSuccessChangePass(message: changePasswordRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun OnStart() {
        EditeProfileBtn?.visibility=View.GONE
        ProgEditProfile?.visibility=View.VISIBLE
    }



    override fun onFailer(message: String) {
        context?.toast(message)
        EditeProfileBtn?.visibility=View.VISIBLE
        ProgEditProfile?.visibility=View.GONE
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
        EditeProfileBtn?.visibility=View.VISIBLE
        ProgEditProfile?.visibility=View.GONE
    }



    var cities:MutableList<String> = arrayListOf()

    var cityId:Int=0
    private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        imagesBodyRequest=null
        imagePath=""
        return inflater.inflate(R.layout.fragment_edite_profile, container, false)
    }
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=123
    var imagePath=""
    var imagesBodyRequest: MultipartBody.Part?=null
    var imageFile: RequestBody?=null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= AuthRepostary(api!!)
        val factory= profileViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel::class.java)
        viewModel?.Auth=this
        imagesBodyRequest=null
        imagePath=""

        EditeProfileBtn?.setOnClickListener {
            if(!imagePath.isNullOrEmpty()){
                imagesBodyRequest.let {
                    viewModel?.UpdatePhoto(MainActivity.cacheObj.token,it)
                }
            }

        }


        

        SelectPhotoU?.setOnClickListener {
            if (Build.VERSION.SDK_INT < 19) {
                var intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture")
                    , 0
                )


            } else {
                if (Build.VERSION.SDK_INT >=23) {
                    if(checkPermissionREAD_EXTERNAL_STORAGE(context!!)){
                        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                        intent.action= Intent.ACTION_GET_CONTENT
                        // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                        intent.type = "image/*"
                        startActivityForResult(intent, 0);
                    }else{
                        context?.toast("you can not pick images")
                    }
                }else{
                    //  var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    var intent = Intent()

                    intent.action= Intent.ACTION_GET_CONTENT
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    startActivityForResult(intent, 0);
                }


            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        // When an Image is picked
        if (requestCode == 0 && resultCode == Activity.RESULT_OK
            && null != data
        ) {
            var imageUri: Uri? = data.data
            val bitmap= MediaStore.Images.Media.getBitmap(context?.contentResolver,imageUri)
            // val bitmapDrawable= BitmapDrawable(bitmap)
            SelectPhotoU.text=""
            profileImage.setImageBitmap(bitmap)
            SelectPhotoU.alpha=0f
            getPathFromURI(imageUri!!)
            var oregnal = File(imagePath)
//  var oregnal = File(getPathFromURI(imageUri))
            imageFile = RequestBody.create(
                MediaType.parse(context?.contentResolver?.getType(imageUri)!!),
                oregnal
            )
            imagesBodyRequest= MultipartBody.Part.createFormData("file", oregnal.name, imageFile)

        }

    }

    @SuppressLint("NewApi")
    fun getPathFromURI(uri: Uri) {
        var path: String = uri.path!! // uri = any content Uri

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.DATE_TAKEN
            ) // some example data you can query
            val cursor = context?.contentResolver ?.query(
                databaseUri,
                projection, selection, selectionArgs, null
            )
            if(cursor==null){
                imagePath = path
            }else
                if (cursor!!.moveToFirst()) {
                    val columnIndex = cursor!!.getColumnIndex(projection[0])
                    //  if (cursor.getType(columnIndex) == FIELD_TYPE_STRING) {
                    imagePath = cursor!!.getString(columnIndex)
                    // }

                    // Log.e("path", imagePath)
                }


            cursor?.close()
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message, e)
        }
    }

    fun checkPermissionREAD_EXTERNAL_STORAGE(
        context: Context
    ):Boolean {
        var currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                showDialog("External storage", context,
//                    Manifest.permission.READ_EXTERNAL_STORAGE);
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                        .requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) ,
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    fun showDialog(msg:String, context: Context,
                   permission:String) {
        var alertBuilder: AlertDialog.Builder = AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
            DialogInterface.OnClickListener(
                fun(dialog: DialogInterface, which:Int) {
                    ActivityCompat.requestPermissions(context as Activity,
                        arrayOf(permission ),
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            )



        )
        val  alert: AlertDialog = alertBuilder.create();
        alert.show();
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
//                    var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                    intent.action= Intent.ACTION_GET_CONTENT
//                    // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                    intent.addCategory(Intent.CATEGORY_OPENABLE)
//                    intent.type = "image/*"
//                    startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    context?.toast("tou can not pick image")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
