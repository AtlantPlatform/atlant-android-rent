package io.atlant.rent.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import io.atlant.rent.R
import io.atlant.rent.model.SelectedApp
import io.atlant.rent.utils.PicassoTargetUtils
import io.atlant.rent.utils.ScreenUtils
import io.atlant.rent.view.ImageViewRound
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import java.util.*

class SelectedAppPagerAdapter(private val context: Context, private val arrayList: ArrayList<SelectedApp>) : BaseAdapterScrollCircular<SelectedApp>(arrayList) {

    @BindView(R.id.adapter_selected_app_progress_bar)
    lateinit var progressBar: MaterialProgressBar
    @BindView(R.id.adapter_selected_app_relative)
    lateinit var relativeLayout: RelativeLayout
    @BindView(R.id.adapter_selected_app_image)
    lateinit var imageView: ImageViewRound
    @BindView(R.id.adapter_selected_app_title)
    lateinit var textTitle1: TextView
    @BindView(R.id.adapter_selected_app_title2)
    lateinit var textTitle2: TextView
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
        ButterKnife.bind(this, view)

        val picassoTargetUtils = PicassoTargetUtils(progressBar!!, imageView!!)
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

        relativeLayout.setOnClickListener {
            if (callBack != null) {
                callBack!!.onClickItems(position - 1)
            }
        }

        textTitle1.text = arrayList[position].title1
        textTitle2.text = arrayList[position].title2
        container.addView(view)
        return view
    }
}
