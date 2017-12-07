package io.atlant.rent.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import io.atlant.rent.R
import io.atlant.rent.model.RentCity
import io.atlant.rent.utils.PicassoTargetUtils
import io.atlant.rent.utils.ScreenUtils
import io.atlant.rent.view.ImageViewRound
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
        val context = holder.relativeLayout.context

        Picasso.with(context).load(rent.pathImage).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.ic_warning).into(holder.target.target)

        holder.textName.text = rent.name

        holder.relativeLayout.setOnClickListener {
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

        @BindView(R.id.adapter_city_relative)
        lateinit var relativeLayout: RelativeLayout
        @BindView(R.id.adapter_city_progress_bar)
        lateinit var materialProgressBar: MaterialProgressBar
        @BindView(R.id.adapter_city_image_round_view)
        lateinit var imageView: ImageViewRound
        @BindView(R.id.adapter_city_name_text)
        lateinit var textName: TextView

        var target: PicassoTargetUtils

        init {
            ButterKnife.bind(this, view)
            target = PicassoTargetUtils(materialProgressBar, imageView)
        }
    }

}
