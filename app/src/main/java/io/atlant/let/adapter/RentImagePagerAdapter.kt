/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package io.atlant.let.adapter

import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.atlant.let.R
import io.atlant.let.utils.PicassoTargetUtils
import io.atlant.let.utils.PicassoTargetUtils.CallBack
import io.atlant.let.utils.ScreenUtils
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
