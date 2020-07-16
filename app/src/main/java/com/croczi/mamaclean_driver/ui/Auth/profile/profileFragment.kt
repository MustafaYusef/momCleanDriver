package com.croczi.mamaclean.ui.Auth.profile

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.croczi.mamaclean.data.auth.changePasswordRes
import com.croczi.mamaclean.data.auth.profile.myPackage.myPackages
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.utils.showMessageOKCancel
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.auth.profile.profile.profileRes
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.AuthRepostary
import com.mustafayusef.wakely.ui.auth.ProfileLesener
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pop_dilog.view.*
import kotlinx.android.synthetic.main.profile_fragment.*

class profileFragment : Fragment(),ProfileLesener {
    override fun OnSuccessUpdatePhoto(message: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnSuccessChangePass(message: changePasswordRes) {
    }


    override fun OnStart() {
        progressProf?.visibility=View.VISIBLE
    }


    override fun onFailer(message: String) {
        //context?.toast(message)
        progressProf?.visibility=View.GONE
        if(message.toInt()==403){
            view?.findNavController()?.navigate(R.id.login)
            context?.toast("الحساب غير صالح")
        }
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
        progressProf?.visibility=View.GONE
    }


    var proPar:profileRes?=null
    override fun OnSuccessProfile(message: profileRes) {
        progressProf?.visibility=View.GONE
        proPar=message
        MainActivity.cacheObj.name=message.data.profile.name
        MainActivity.cacheObj.photo=message.data.profile.photo
//        val header: View = nav_view.getHeaderView(0)
//        var mNameTextView = header.findViewById<View>(R.id.driverTitle) as TextView


//        var photo = header.findViewById<View>(R.id.drwarImage) as CircleImageView



        userNameProfile?.text=message.data.profile.name
        phoneProfile?.text=message.data.profile.phone
        if(message.data.profile.online){
            MainActivity.cacheObj.state=true
            changeStatus?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mode_edit_black_24dp,0,R.drawable.ic_radio_button_checked_black_24dp,0)
            changeStatus?.text="نشط"
        }else{
            changeStatus?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mode_edit_black_24dp,0,
                R.drawable.ic_radio_button_checked_red_24dp,0)
            changeStatus?.text="غير نشط"
            MainActivity.cacheObj.state=false
        }

        profileImage?.let {it1->
            context?.let {
                Glide.with(it).load(MainActivity.constant.base+message.data.profile.photo)
                    .placeholder(R.drawable.logo).apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                    .into( it1)
            }
        }
        changeStatus?.setOnClickListener {
            val dview: View =layoutInflater.inflate(R.layout.pop_dilog, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart?.text="هل تريد تغيير حالتك ؟"
            dview.conform?.setOnClickListener {
                viewModel?.changeStatus(MainActivity.cacheObj.token)
                malert?.dismiss()
            }



        }


//        if(arguments!!.getBoolean("isActive")){
//            var bundle=Bundle()
//            bundle.putString("phone",message.data.profile.phone)
//            view?.findNavController()?.navigate(R.id.activateUser,bundle)
//        }

//        profileImage?.let {it1->
//            context?.let {
//                Glide.with(it).load(MainActivity.constant.base+message.data.profile.photo)
//                    .into( it1)
//            }
//        }
    }

    override fun OnSuccessChangeStatus(message: reSendRes) {
        progressProf?.visibility=View.GONE
        viewModel?.getProfile(MainActivity.cacheObj.token)
    }

    companion object {
        fun newInstance() = profileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= AuthRepostary(api!!)
        val factory=profileViewModelFactory (repostary)

        viewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel::class.java)
        viewModel?.Auth=this


        if(MainActivity.cacheObj.token.isNullOrEmpty()){
            view?.findNavController()?.navigate(R.id.login)
        }else{
            viewModel?.getProfile(MainActivity.cacheObj.token)
        }
        editeProfile?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.editeProfile2)

        }

        changePassBt?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.changePassword)
        }

    }

}
