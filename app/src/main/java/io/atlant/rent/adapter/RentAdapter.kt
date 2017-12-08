package io.atlant.rent.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.atlant.rent.R
import io.atlant.rent.model.Rent
import io.atlant.rent.utils.PicassoTargetUtils
import io.atlant.rent.utils.ScreenUtils
import io.atlant.rent.view.ImageViewRound
import io.atlant.rent.view.LikeView
import kotlinx.android.synthetic.main.adapter_rent.view.*
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import java.util.*

class RentAdapter(private val arrayItems: ArrayList<Rent>) : RecyclerView.Adapter<RentAdapter.MyViewHolder>() {
    private var callBack: CallBack? = null

    interface CallBack {

        fun onSelected(pos: Int)
    }

    fun setCallBack(callBack: CallBack) {

        this.callBack = callBack
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var linearLayout: LinearLayout? = null
        var materialProgressBar: MaterialProgressBar? = null
        var imageView: ImageViewRound? = null
        var textName: TextView? = null
        var textCity: TextView? = null
        var textRooms: TextView? = null
        var textBeds: TextView? = null
        var textPrice: TextView? = null
        var textHowDay: TextView? = null
        var imTriangle: ImageView? = null
        var likeView: LikeView? = null

        var target: PicassoTargetUtils

        init {
            linearLayout = view.adapter_rent_linear
            materialProgressBar = view.adapter_rent_progress_bar
            imageView = view.adapter_rent_image
            textName = view.adapter_rent_name_text
            textCity = view.adapter_rent_address_text
            textRooms = view.adapter_rent_rooms_text
            textBeds = view.adapter_rent_beds_text
            textPrice = view.adapter_rent_price_text
            textHowDay = view.adapter_rent_how_day_text
            imTriangle = view.adapter_rent_down_up
            likeView = view.adapter_rent_like

            target = PicassoTargetUtils(materialProgressBar!!, imageView!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_rent, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rent = arrayItems[position]
        val context = holder.linearLayout!!.context

        holder.linearLayout!!.setOnClickListener {
            if (callBack != null) {
                callBack!!.onSelected(position)
            }
        }

        Picasso.with(context).load(rent.imageUrl!![0]).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.ic_warning).into(holder.target.target!!)

        holder.textName!!.text = rent.name
        holder.textCity!!.text = rent.country + ", " + rent.city
        val rooms = context.getString(R.string.rent_main_rooms)
        holder.textRooms!!.text = rent.numberRooms.toString() + " " + rooms
        val beds = context.getString(R.string.rent_main_beds)
        holder.textBeds!!.text = rent.numberBeds.toString() + " " + beds
        holder.textPrice!!.text = "$ " + rent.priceDollars.toString()
        holder.textHowDay!!.text = rent.howDay.toString()
        holder.likeView!!.like = rent.numberLike

        if (rent.isGrowth) {
            holder.imTriangle!!.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_rent_up))
        } else {
            holder.imTriangle!!.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_rent_down))
        }
    }

    override fun getItemCount(): Int {
        return arrayItems.size
    }

}
