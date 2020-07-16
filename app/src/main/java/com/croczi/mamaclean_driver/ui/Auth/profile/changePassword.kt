package com.croczi.mamaclean.ui.Auth.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_change_password.*

class changePassword : Fragment(),ProfileLesener {
    override fun OnSuccessProfile(message: profileRes) {
    }

    override fun OnSuccessChangeStatus(message: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnSuccessUpdatePhoto(message: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun OnSuccessChangePass(message: changePasswordRes) {
        changePassBtn?.visibility=View.VISIBLE
        view?.findNavController()?.navigate(R.id.profileFragment2)
    }

    override fun OnStart() {
        changePassBtn?.visibility=View.GONE
    }

    override fun onFailer(message: String) {
//        context?.toast(message)
        changePassBtn?.visibility=View.VISIBLE
        if(message.toInt()==403){
            view?.findNavController()?.navigate(R.id.login)
            context?.toast("الحساب غير صالح")
        }
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
        changePassBtn?.visibility=View.VISIBLE
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }
    private lateinit var viewModel: ProfileViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter = context?.let { networkIntercepter(it) }
        val api = networkIntercepter?.let { myApi(it) }
        val repostary = AuthRepostary(api!!)
        val factory = profileViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        viewModel?.Auth = this

        changePassBtn?.setOnClickListener {
            if(!oldPassword?.text.toString().isNullOrEmpty()&& !newPassword?.text .toString().isNullOrEmpty()){
                viewModel?.changePassword(
                    MainActivity.cacheObj.token,
                    oldPassword?.text.toString(),newPassword?.text.toString())

            }
        }
    }
}
