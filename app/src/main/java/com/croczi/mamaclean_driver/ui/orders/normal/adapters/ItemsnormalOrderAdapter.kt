package com.croczi.mamaclean.ui.orders.adapters


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.croczi.mamaclean.ui.orders.OrderViewModel

import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersRes.ordersNormalRes

import kotlinx.android.synthetic.main.order_package_card.view.*
import kotlinx.android.synthetic.main.pop_dilog.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.hours


class ItemsnormalOrderAdapter(
    val context: Context,
    val response: ordersNormalRes,
    val viewModel: OrderViewModel,
    val status:String
) : RecyclerView.Adapter<ItemsnormalOrderAdapter.CustomViewHolder>(){
//
 // private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater =LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(R.layout.order_package_card ,parent,false)

        return CustomViewHolder(cardItem)


    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
    return response.data.orders.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        var data=response.data.orders.get (position)
        holder.view.titleOrder?.text="طلب اعتيادي"
        holder.view.priceOrder?.text=data.price.toString()+" "+"IQ"
        holder.view.OrderCount?.text="العدد: "+data.itemsCount.toString()

        if(status=="cancelled"||status=="delivered"){
            holder.view.recevedClient.visibility=View.GONE
            holder.view.busyClient  .visibility=View.GONE
            holder.view.deleverdToOffice.visibility=View.GONE
        }else if(status=="received"){
            holder.view.recevedClient.visibility=View.GONE
            holder.view.busyClient.visibility=View.GONE
            holder.view.deleverdToOffice.visibility=View.VISIBLE

        }else if(status=="busy"){
            holder.view.recevedClient.visibility=View.VISIBLE
            holder.view.busyClient.visibility=View.GONE
            holder.view.deleverdToOffice.visibility=View.GONE
        }
        holder.view.busyClient.setOnClickListener {
            val dview: View =View.inflate(context, R.layout.pop_dilog, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart?.text="هل تريد تغيير الحالة؟"
            dview.conform?.setOnClickListener {
                viewModel.changeOrderStatus(MainActivity.cacheObj.token,data.id,"busy")

                malert?.dismiss()
            }
        }

        holder.view.recevedClient.setOnClickListener {
            val dview: View =View.inflate(context, R.layout.pop_dilog, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart?.text="هل تريد تغيير الحالة؟"
            dview.conform?.setOnClickListener {
                viewModel.changeOrderStatus(MainActivity.cacheObj.token,data.id,"received")

                malert?.dismiss()
            }
        }

        holder.view.deleverdToOffice.setOnClickListener {
            val dview: View =View.inflate(context, R.layout.pop_dilog, null)
            val builder = context?.let { AlertDialog.Builder(it).setView(dview) }
            val malert= builder?.show()
            malert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dview?.descPart?.text="هل تريد تغيير الحالة؟"
            dview.conform?.setOnClickListener {
                viewModel.deleverOrder(MainActivity.cacheObj.token,data.id)

                malert?.dismiss()
            }
        }

        holder.view.locationOrder?.setOnClickListener {
            var bundel=Bundle()
            bundel.putString("name",data.user.name)
            bundel.putDouble("lat",data.latitude.toDouble())
            bundel.putDouble("lon",data.longitude.toDouble())
         holder.view.findNavController()?.navigate(R.id.mapOrderFragment,bundel)
        }

        holder.view.setOnClickListener {
            var bundel=Bundle()
            bundel.putInt("id",data.id)
            bundel.putString("name",data.user.name)
            bundel.putString("phone",data.user.phone)
            bundel.putDouble("lat",data.latitude.toDouble())
            bundel.putDouble("lon",data.longitude.toDouble())
            bundel.putBoolean("flage",true)
            holder.view.findNavController()?.navigate(R.id.orderDetailsFragment,bundel)
        }
    }



    class CustomViewHolder(val view : View) : RecyclerView.ViewHolder(view){
//           var OnNotlesener:OnNoteLisener
//        override fun onClick(view: View?) {
//           onNoteLisener.onNoteClick(layoutPosition)
//        }
//
//        init {
//            this.OnNotlesener=onNoteLisener
//         view.setOnClickListener(this)
//
//        }


    }






}

