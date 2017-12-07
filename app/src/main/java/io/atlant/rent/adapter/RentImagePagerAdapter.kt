package io.atlant.rent.adapter

import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import io.atlant.rent.R
import io.atlant.rent.utils.PicassoTargetUtils
import io.atlant.rent.utils.PicassoTargetUtils.CallBack
import io.atlant.rent.utils.ScreenUtils
import io.atlant.rent.view.ImageViewRound
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import java.util.*

class RentImagePagerAdapter(private val context: Context, private val arrayListURL: ArrayList<String>) : PagerAdapter() {

    @BindView(R.id.adapter_detail_progress_bar)
    lateinit var progressBar: MaterialProgressBar
    @BindView(R.id.adapter_detail_gallery_image)
    lateinit var imageView: ImageViewRound
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return arrayListURL.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = inflater.inflate(R.layout.adapter_detail_gallery, container, false)
        ButterKnife.bind(this, view)
        val picassoTargetUtils = PicassoTargetUtils(progressBar, imageView)
        picassoTargetUtils.setCallBack(object : CallBack {
            override fun onBitmapLoaded() {

            }

            override fun onBitmapFailed() {

            }

            override fun onPrepareLoad() {
                val handler = Handler()
                handler.postDelayed({
                    Picasso.with(context).load(arrayListURL[position]).resize(ScreenUtils.getWidth(context), 0)
                            .error(R.drawable.ic_warning).into(picassoTargetUtils.target!!)
                }, 10)
            }
        })

        Picasso.with(context).load(arrayListURL[position]).resize(ScreenUtils.getWidth(context), 0)
                .error(R.drawable.ic_warning).into(picassoTargetUtils.target!!)
        container.addView(view)
        return view
    }
}
