package com.croczi.mamaclean.ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.croczi.mamaclean.data.auth.signUp.signUpResponse
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.data.auth.verification.verficationRes

import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.AuthLesener
import com.mustafayusef.wakely.ui.auth.AuthRepostary
import kotlinx.android.synthetic.main.forget_password.*


class forgetPassword : Fragment(),AuthLesener {
    override fun OnSuccesSignIn(response: signUpResponse) {

    }

    override fun OnSuccessSendCode(message: reSendRes) {
        context?.toast("تم ارسال الرمز")

    }

    override fun OnSuccessVerefication(it1: verficationRes) {

    }

    override fun OnSuccessResetPassword(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun OnStart() {

    }



    override fun onFailer(message: String) {
        //context?.toast(message)
        if(message.toInt()==403){
            view?.findNavController()?.navigate(R.id.login)
            context?.toast("الحساب غير صالح")
        }
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")

    }



    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forget_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val toolbar = activity!!.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.visibility=View.GONE
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }
        val repostary= AuthRepostary(api!!)
        val factory= AuthViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel::class.java)
        viewModel?.Auth=this
        sendCodForget?.setOnClickListener {
           if(!phoneNumForget.text.toString().isNullOrEmpty()){
             var bundel=Bundle()
               bundel.putBoolean("flage",true)
               bundel.putString("phone",ccpForget.fullNumberWithPlus+phoneNumForget.text.toString())
               view?.findNavController()?.navigate(R.id.activateUser2,bundel)
           }

          // viewModel?.ActivateUser(ccpForget.fullNumberWithPlus+phoneNumForget.text.toString())
        }
    }
}
