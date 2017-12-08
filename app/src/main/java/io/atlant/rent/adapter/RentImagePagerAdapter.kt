package io.atlant.rent.adapter

import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.atlant.rent.R
import io.atlant.rent.utils.PicassoTargetUtils
import io.atlant.rent.utils.PicassoTargetUtils.CallBack
import io.atlant.rent.utils.ScreenUtils
import kotlinx.android.synthetic.main.adapter_detail_gallery.view.*
import java.util.*

class RentImagePagerAdapter(private val context: Context, private val arrayListURL: ArrayList<String>) : PagerAdapter() {

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
        val picassoTargetUtils = PicassoTargetUtils(view.adapter_detail_progress_bar, view.adapter_detail_gallery_image)
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
