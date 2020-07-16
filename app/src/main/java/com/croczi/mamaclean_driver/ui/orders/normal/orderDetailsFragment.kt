package com.croczi.mamaclean.ui.orders

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes

import com.croczi.mamaclean.ui.orders.adapters.DetailsOrderAdapter
import com.croczi.mamaclean.ui.orders.adapters.DetailsPackageOrderAdapter
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.Order
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails.packageOrderDetailsRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes.packageOrdersRes
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast

import com.mustafayusef.wakely.ui.auth.OrderLesener
import com.mustafayusef.wakely.ui.auth.OrderRepostary
import kotlinx.android.synthetic.main.orders_details_fragment.*
import kotlinx.android.synthetic.main.pop_dilog.view.*

class orderDetailsFragment : Fragment(),OrderLesener {

    companion object {
        fun newInstance() = orderDetailsFragment()
    }

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.orders_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }

        val repostary= OrderRepostary(api!!)
        val factory= OrderViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this,factory).get(OrderViewModel::class.java)
        viewModel?.Auth=this
        ordersDetailsList?.layoutManager=LinearLayoutManager(context!!)
        if(arguments!!.getBoolean("flage",false)){
            viewModel?.getCategoryOrderDetails(MainActivity.cacheObj.token,
                arguments!!.getInt("id"))
        }else{
            viewModel?.getPackageOrderDetails(MainActivity.cacheObj.token,
                arguments!!.getInt("id"))
        }
        OrdersDetailsScroll?.setOnRefreshListener {
            if(arguments!!.getBoolean("flage",false)){
                viewModel?.getCategoryOrderDetails(MainActivity.cacheObj.token,
                    arguments!!.getInt("id"))
            }else{
                viewModel?.getPackageOrderDetails(MainActivity.cacheObj.token,
                    arguments!!.getInt("id"))
            }
        }

        location?.setOnClickListener {
            var bundel=Bundle()
            bundel.putString("name",arguments!!.getString("name"))
            bundel.putDouble("lat",arguments!!.getDouble("lat"))
            bundel.putDouble("lon",arguments!!.getDouble("lon"))
             view?.findNavController()?.navigate(R.id.mapOrderFragment,bundel)
        }
    }
    override fun onSussessGetNormalOrderDetails(it1: ordersDetailsNormalRes) {
        ordersDetailsList?.adapter=DetailsOrderAdapter(context!!,it1)
        clientName?.text=arguments!!.getString("name")
        clientPhone?.text=arguments!!.getString("phone")
        clientName?.text="أسم الزبون: "+arguments!!.getString("name")
        clientPhone?.text="رقم الزبون: "+arguments!!.getString("phone")
        callClient?.setOnClickListener {
            val dview: View = View.inflate(context, R.layout.pop_dilog, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart.text="هل تريد الأتصال"
            dview.conform?.setOnClickListener {
                malert?.dismiss()
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${arguments!!.getString("phone")}")
                }

                context?.startActivity(intent)

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
            }
        }
        OrdersDetailsScroll?.isRefreshing=false
    }
    override fun onSussessGetPackageOrderDetails(it1: packageOrderDetailsRes) {
        ordersDetailsList?.adapter=DetailsPackageOrderAdapter(context!!,it1)
        clientName?.text="أسم الزبون: "+arguments!!.getString("name")
        clientPhone?.text="رقم الزبون: "+arguments!!.getString("phone")
        callClient?.setOnClickListener {
            val dview: View = View.inflate(context, R.layout.pop_dilog, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart.text="هل تريد الأتصال"
            dview.conform?.setOnClickListener {
                malert?.dismiss()
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${arguments!!.getString("phone")}")
                }

                context?.startActivity(intent)

//                context?.toast(context?.getResources().getString(R.string.deletSucc))
            }
        }
        OrdersDetailsScroll?.isRefreshing=false
    }
    override fun OnStart() {
        OrdersDetailsScroll?.isRefreshing=true
    }

    override fun onFailer(message: String) {
       // context?.toast(message)
        OrdersDetailsScroll?.isRefreshing=false
        if(message.toInt()==403){
            view?.findNavController()?.navigate(R.id.login)
            context?.toast("الحساب غير صالح")
        }
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
        OrdersDetailsScroll?.isRefreshing=false
    }

    override fun onSussessGetNormalOrder(it1: ordersNormalRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun onSussessChangeNormalOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessGetPackageOrder(it1: packageOrdersRes) {
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
/*    override fun onSussessGetNormalOrderDetails(it1: categoryOrderDetailsRes) {
        ordersDetailsList?.adapter=DetailsOrderAdapter(context!!,it1)
        OrdersDetailsScroll?.isRefreshing=false
    }
    override fun onSussessGetPackageOrderDetails(it1: packageOrderDetailsRes) {
        ordersDetailsList?.adapter= DetailsPackageOrderAdapter(context!!,it1)
        OrdersDetailsScroll?.isRefreshing=false
    }
    override fun OnStart() {
        OrdersDetailsScroll?.isRefreshing=true
    }

    override fun onFailer(message: String) {
        OrdersDetailsScroll?.isRefreshing=false
    }

    override fun onFailerNet(message: String) {
        OrdersDetailsScroll?.isRefreshing=false
    }

    override fun onSussessGetNormalOrder(it1: categoryOrderRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun onSussessCancelNormalOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessGetPackageOrder(it1: packageOrderRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun onSussessCancelPackageOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

}
