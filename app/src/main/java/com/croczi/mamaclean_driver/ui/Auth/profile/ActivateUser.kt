package com.croczi.mamaclean.ui.Auth.profile

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.croczi.mamaclean.data.auth.signUp.signUpResponse
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.data.auth.verification.verficationRes

import com.croczi.mamaclean.ui.Auth.AuthViewModelFactory
import com.croczi.mamaclean.ui.Auth.LoginViewModel
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.AuthLesener
import com.mustafayusef.wakely.ui.auth.AuthRepostary
import kotlinx.android.synthetic.main.fragment_activate_user.*


class ActivateUser : Fragment(),AuthLesener {
    override fun OnSuccesSignIn(response: signUpResponse) {

    }

    override fun OnSuccessSendCode(message: reSendRes) {
        context?.toast("لقد وصلتك رسالة يرجى التحقق")
    }

    override fun OnSuccessVerefication(it1: verficationRes) {



            context?.toast("تم تفعيل الحساب")
            MainActivity.cacheObj.token=arguments!!.getString("token")!!
        view?.findNavController()?.popBackStack(R.id.activateUser2,true)
        view?.findNavController()?.popBackStack(R.id.login,true)
            view?.findNavController()?.navigate(R.id.profileFragment2)
        }



    override fun OnSuccessResetPassword(it1: reSendRes) {

    }

    override fun OnStart() {
        context?.toast("سوف تأتيك رسالة بعد قليل")
    }

    override fun onFailer(message: String) {
        context?.toast(message)
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
    }






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activate_user, container, false)
    }
    private lateinit var viewModel: LoginViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val toolbar = activity!!.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.visibility=View.GONE
        val networkIntercepter = context?.let { networkIntercepter(it) }
        val api = networkIntercepter?.let { myApi(it) }
        val repostary = AuthRepostary(api!!)
        val factory = AuthViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel?.Auth = this
        arguments!!.getString("phone")?.let { viewModel?.ActivateUser(it)}

        if(arguments?.getBoolean("flage",false)!!){

        }
        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown?.text="أضغط على اعادة الأرسال بعد\n"+(millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                ReSendCode?.setOnClickListener {
                    arguments!!.getString("phone")?.let { viewModel?.ActivateUser(it)}
                }
            }
        }
        timer.start()

        activateBtn?.setOnClickListener {
            if(!confirmCode?.text.toString().isNullOrEmpty()){
                if(arguments?.getBoolean("flage",false)!!){
                    arguments!!.getString("phone")?.let { it1 ->
                        var bundel=Bundle()
                        bundel.putString("phone",it1)
                        bundel.putString("code",confirmCode?.text.toString())
                        view?.findNavController()?.navigate(R.id.resetPassword,bundel)
                    }
                }else{
                    arguments!!.getString("phone")?.let { it1 ->
                        viewModel?.VerivecationUser(confirmCode?.text.toString(),
                            it1
                        )
                    }
                }
                    }






        }
    }
}
