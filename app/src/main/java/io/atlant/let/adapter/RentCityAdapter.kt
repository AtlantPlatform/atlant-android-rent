/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.atlant.let.R
import io.atlant.let.model.RentCity
import io.atlant.let.utils.PicassoTargetUtils
import io.atlant.let.utils.ScreenUtils
import io.atlant.let.view.ImageViewRound
import kotlinx.android.synthetic.main.adapter_city.view.*
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import java.util.*

class RentCityAdapter(private val arrayItems: ArrayList<RentCity>) : RecyclerView.Adapter<RentCityAdapter.MyViewHolder>() {
    private var callBack: CallBack? = null

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_city, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rent = arrayItems[position]
        val context = holder.relativeLayout!!.context

        Picasso.with(context).load(rent.pathImage).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.ic_warning).into(holder.target.target)

        holder.textName!!.text = rent.name

        holder.relativeLayout!!.setOnClickListener {
            if (callBack != null) {
                callBack!!.onCityClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayItems.size
    }

    interface CallBack {

        fun onCityClick(pos: Int)

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var relativeLayout: RelativeLayout? = null
        var materialProgressBar: MaterialProgressBar? = null
        var imageView: ImageViewRound? = null
        var textName: TextView? = null

        var target: PicassoTargetUtils

        init {
            relativeLayout = view.adapter_city_relative
            materialProgressBar = view.adapter_city_progress_bar
            imageView = view.adapter_city_image_round_view
            textName = view.adapter_city_name_text

            target = PicassoTargetUtils(materialProgressBar!!, imageView!!)
        }
    }

}
