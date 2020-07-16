package com.croczi.mamaclean.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.croczi.mamaclean.data.auth.verification.reSendCode.reSendRes
import com.croczi.mamaclean.ui.orders.adapters.ItemsPackageOrderAdapter
import com.croczi.mamaclean.ui.orders.adapters.ItemsnormalOrderAdapter
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderDetails.packageOrderDetailsRes
import com.croczi.mamaclean_driver.data.orders.packageOrder.packageOrderRes.packageOrdersRes
import com.croczi.mamaclean_driver.network.myApi
import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.holidaymaster.utils.toast
import com.mustafayusef.wakely.ui.auth.OrderLesener
import com.mustafayusef.wakely.ui.auth.OrderRepostary
import kotlinx.android.synthetic.main.orders_fragment.*


class ordersFragment : Fragment(),OrderLesener {

    companion object {
        fun newInstance() = ordersFragment()
    }

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    var flage=true
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val networkIntercepter= context?.let { networkIntercepter(it) }
        val api= networkIntercepter?.let { myApi(it) }

        val repostary= OrderRepostary(api!!)
        val factory= OrderViewModelFactory(repostary)

        viewModel = ViewModelProviders.of(this,factory).get(OrderViewModel::class.java)
        viewModel?.Auth=this
        ordersList?.layoutManager=LinearLayoutManager(context!!)
        viewModel?.getNormalOrder(MainActivity.cacheObj.token,25,0,"default")
        OrdersScroll.setOnRefreshListener {
           if(flage) {

               viewModel?.getNormalOrder(MainActivity.cacheObj.token,25,0,"default")
           }else{
               viewModel?.getPackageOrder(MainActivity.cacheObj.token,25,0,"default")
           }
        }
//        ordersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                  if(flage){
//                      viewModel?.getNormalOrder(MainActivity.cacheObj.token,10,1,"default")
//
//                  }else{
//                      viewModel?.getPackageOrder(MainActivity.cacheObj.token,10,1,"default")
//
//                  }
//                }
//            }
//        })
//
        normalOrder?.setOnClickListener {
            flage=true
            normalOrder?.setBackgroundResource(R.drawable.round_btn4)

            normalOrder?.setTextColor(context!!.resources.getColor(R.color.white))

            packageOrder?.setBackgroundResource(R.drawable.back_text)

            packageOrder?.setTextColor(context!!.resources.getColor(R.color.darkgray))
            viewModel?.getNormalOrder(MainActivity.cacheObj.token,25,0,"default")

        }
        packageOrder?.setOnClickListener {
            flage=false
            packageOrder?.setBackgroundResource(R.drawable.round_btn4)

            packageOrder?.setTextColor(context!!.resources.getColor(R.color.white))

            normalOrder?.setBackgroundResource(R.drawable.back_text)

            normalOrder?.setTextColor(context!!.resources.getColor(R.color.darkgray))
            viewModel?.getPackageOrder(MainActivity.cacheObj.token,25,0,"default")

        }

    }

    override fun OnStart() {
        OrdersScroll?.isRefreshing=true
    }


    override fun onFailer(message: String) {
        OrdersScroll?.isRefreshing=false
        if(message.toInt()==403){
            view?.findNavController()?.navigate(R.id.login)
            context?.toast("الحساب غير صالح")
        }
    }

    override fun onFailerNet(message: String) {
        context?.toast("لا يوجد اتصال")
        OrdersScroll?.isRefreshing=false  //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessGetNormalOrder(it1: ordersNormalRes) {
        ordersList?.adapter=ItemsnormalOrderAdapter(context!!,it1,viewModel,"default")
        OrdersScroll?.isRefreshing=false
    }

    override fun onSussessGetNormalOrderDetails(it1: ordersDetailsNormalRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessChangeNormalOrder(it1: reSendRes) {
        viewModel?.getNormalOrder(MainActivity.cacheObj.token,25,0,"default")

        OrdersScroll?.isRefreshing=false
    }

    override fun onSussessGetPackageOrder(it1: packageOrdersRes) {
        ordersList?.adapter=ItemsPackageOrderAdapter(context!!,it1,viewModel,"default")
        OrdersScroll?.isRefreshing=false//To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessGetPackageOrderDetails(it1: packageOrderDetailsRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessChangePackageOrder(it1: reSendRes) {
        context?.toast(it1.message)
        viewModel?.getPackageOrder(MainActivity.cacheObj.token,25,0,"default")
        OrdersScroll?.isRefreshing=false
    }

    override fun onSussessDeleverNormalOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSussessDeleverPackageOrder(it1: reSendRes) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
