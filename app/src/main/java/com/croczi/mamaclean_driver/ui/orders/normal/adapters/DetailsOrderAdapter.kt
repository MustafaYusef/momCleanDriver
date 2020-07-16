package com.croczi.mamaclean.ui.orders.adapters


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.croczi.mamaclean.ui.orders.OrderViewModel
import com.croczi.mamaclean_driver.MainActivity
import com.croczi.mamaclean_driver.R
import com.croczi.mamaclean_driver.data.orders.normalOrder.ordersDetails.ordersDetailsNormalRes
import kotlinx.android.synthetic.main.items_cart_card.view.*
import kotlinx.android.synthetic.main.items_details_order_card.view.*
import kotlinx.android.synthetic.main.order_package_card.view.*
import java.text.SimpleDateFormat
import java.util.*


class DetailsOrderAdapter(
    val context: Context,
    val response: ordersDetailsNormalRes
) : RecyclerView.Adapter<DetailsOrderAdapter.CustomViewHolder>(){
//
 // private  var mOnNotlesener=onNoteLisener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // println(holidayFeed)

        val layoutInflater =LayoutInflater.from(parent.context)
        val cardItem=layoutInflater.inflate(R.layout.items_details_order_card ,parent,false)

        return CustomViewHolder(cardItem)


    }

    override fun getItemCount(): Int {
        // count=holidayFeed!!.count().toString()
    return response.data.order.orderItmes.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
      //  holder.view.containerCar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
        //holder.view. OneContainer.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_in_list))

        var data=response.data.order.orderItmes.get (position)

        holder.view.titleOrderItem.text=data.item.name_ar
        holder.view. countOrderItem.text="العدد: "+data.count.toString()


        Glide.with(context).load(
            MainActivity.constant.base+
                data.item.photo) .placeholder(R.drawable.image2).apply(
            RequestOptions.bitmapTransform(
                RoundedCorners(25)
            ))
            .into( holder.view.ItemeOrderImage)


//        else{
//            var data=response.data.packageItems.packageItems .get(position-1)
//            holder.view. titlePackageItem.text=data.item.name_ar
//            holder.view.WashPrice.text=data.item.wash_price .toString()+" IQD"
//            holder.view.ironWashPrice.text=data.item.dw_price .toString()+" IQD"
//            Glide.with(context).load(MainActivity.constant.base+data.item.photo)
//                .placeholder(R.drawable.laundry)
//                .into( holder.view.ItemeImage)
//
//            holder.view.selectItem?.setOnClickListener {
//                showBottomSheetPackage(context,data.count,viewModel,data,response.data.packageItems.id)
//            }
//        }
        // holder.view.LogoAir .startAnimation(AnimationUtils.loadAnimation(context,R.anim.left_to_right))



//        holder.view.purPackage.setOnClickListener {
//            holder.view.findNavController().navigate(R.id.mapFragment)
//        }

//        Glide.with(context).load("http://api.alwakiel.com/storage/images/"+
//                data.productPrices[0].image)
//            .into(  holder.view.circleImageViewProd)
//        holder.view.setOnClickListener {
//            var bundel=Bundle()
//            bundel.putSerializable("product",data)
//            holder.view.findNavController().navigate(R.id.productDetails,bundel)
//        }

//        Glide.with(context).load("http://api.centralmarketiq.com/"+carsP.image+".png").into(holder.view?.numImage)

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

