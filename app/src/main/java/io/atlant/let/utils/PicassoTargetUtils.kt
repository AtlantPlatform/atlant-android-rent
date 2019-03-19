/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView.ScaleType
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import io.atlant.let.view.ImageViewRound
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class PicassoTargetUtils(private val materialProgressBar: MaterialProgressBar, private val imageView: ImageViewRound) {

    var target: Target? = null
    private var callBack: CallBack? = null

    interface CallBack {

        fun onBitmapLoaded()

        fun onBitmapFailed()

        fun onPrepareLoad()

    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    init {
        createTarget()
    }

    private fun createTarget() {
        target = object : Target {
            override fun onBitmapFailed(errorDrawable: Drawable?) {
                materialProgressBar.visibility = View.INVISIBLE
                imageView.scaleType = ScaleType.CENTER_INSIDE
                imageView.setImageDrawable(errorDrawable)
                if (callBack != null) {
                    callBack!!.onBitmapFailed()
                }
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                materialProgressBar.visibility = View.INVISIBLE
                imageView.scaleType = ScaleType.CENTER_CROP
                imageView.setColor(Color.TRANSPARENT)
                imageView.setImageBitmap(bitmap)
                imageView.invalidate()
                if (callBack != null) {
                    callBack!!.onBitmapLoaded()
                }
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                if (callBack != null) {
                    callBack!!.onPrepareLoad()
                }
            }
        }
    }
}

