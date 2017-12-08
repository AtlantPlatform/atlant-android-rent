package io.atlant.rent.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.atlant.rent.R
import io.atlant.rent.model.SelectedApp
import io.atlant.rent.utils.PicassoTargetUtils
import io.atlant.rent.utils.ScreenUtils
import kotlinx.android.synthetic.main.adapter_selected_app.view.*
import java.util.*

class SelectedAppPagerAdapter(private val context: Context, private val arrayList: ArrayList<SelectedApp>) : BaseAdapterScrollCircular<SelectedApp>(arrayList) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var callBack: CallBack? = null

    interface CallBack {

        fun onClickItems(pos: Int)

    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    override fun createView(container: ViewGroup, position: Int): View {
        val view = inflater.inflate(R.layout.adapter_selected_app, container, false)
        val picassoTargetUtils = PicassoTargetUtils(view.adapter_selected_app_progress_bar, view.adapter_selected_app_image)
        picassoTargetUtils.setCallBack(object : PicassoTargetUtils.CallBack {
            override fun onBitmapLoaded() {}

            override fun onBitmapFailed() {}

            override fun onPrepareLoad() {
                val handler = Handler()
                handler.postDelayed({
                    Picasso.with(context).load(arrayList[position].url).resize(ScreenUtils.getWidth(context), 0)
                            .error(R.drawable.ic_warning).into(picassoTargetUtils.target!!)
                }, 10)
            }
        })

        Picasso.with(context).load(arrayList[position].url).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.ic_warning).into(picassoTargetUtils.target!!)

        view.adapter_selected_app_relative.setOnClickListener {
            if (callBack != null) {
                callBack!!.onClickItems(position - 1)
            }
        }

        view.adapter_selected_app_title.text = arrayList[position].title1
        view.adapter_selected_app_title2.text = arrayList[position].title2
        container.addView(view)
        return view
    }
}
