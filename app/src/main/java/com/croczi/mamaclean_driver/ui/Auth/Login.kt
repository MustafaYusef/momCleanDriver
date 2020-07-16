package com.croczi.mamaclean.ui.Auth

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController



import com.croczi.mamaclean.data.auth.signUp.signUpResponse
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.data.auth.verification.verficationRes
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.network.myApi


import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.AuthLesener
import com.mustafayusef.wakely.ui.auth.AuthRepostary
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.login_fragment.*

class Login : Fragment(),AuthLesener {


    override fun OnSuccessSendCode(message: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnSuccessVerefication(it1: verficationRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnSuccessResetPassword(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    override fun OnStart() {
      logInBt?.visibility=View.GONE
    }

    override fun OnSuccesSignIn(response: signUpResponse) {


        println("token  :"+response.data.token)
        if(!response.data.isActive){
            var bundel=Bundle()
            bundel.putString("token",response.data.token)
            bundel.putString("phone",phone)
          view?.findNavController()?.navigate(R.id.activateUser2,bundel)
        }else{
            MainActivity.cacheObj.token=response.data.token
            //context?.toast(response.message)
            view?.findNavController()?.popBackStack(R.id.login,true)
            view?.findNavController()?.navigate(R.id.profileFragment2)
            logInBt?.visibility=View.VISIBLE

        }


    }

    override fun onFailer(message: String) {
        logInBt?.visibility=View.VISIBLE
        context?.toast(message)
    }

    override fun onFailerNet(message: String) {
        logInBt?.visibility=View.VISIBLE
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
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    var phone=""
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


        logInBt?.setOnClickListener {
            phone=ccp.fullNumberWithPlus+ phoneLogin?.text.toString()
            val playerId:String= OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId
            if(!phoneLogin?.text.toString().isNullOrEmpty()||!passwordLogin?.text.toString().isNullOrEmpty()){
                if(phoneLogin?.text.toString().length==10){
                    viewModel.LogIn(ccp.fullNumberWithPlus+ phoneLogin?.text.toString(),passwordLogin?.text.toString(),playerId)
                }else{
                    context?.toast("يجب ان يتكون الرقم من 10 مراتب")
                }
            }else{
                context?.toast("اكمل جميع الحقول")
            }

        }
        forgetPass?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.forgetPassword)
        }

    }

}
