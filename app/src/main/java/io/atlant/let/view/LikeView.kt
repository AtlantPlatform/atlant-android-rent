/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.view

import android.content.Context
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import io.atlant.let.R

class LikeView : LinearLayout, OnClickListener {

    private var linearLayout: LinearLayout? = null
    private var imageView: ImageView? = null
    private var text: TextView? = null
    private var number = 0
    private var isShadows = false
    private var orientationView=0

    var like: Int
        get() = number
        set(number) {
            this.number = number
            text!!.text = number.toString()
        }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LikeView)
        try {
            number = typedArray.getInteger(R.styleable.LikeView_lv_number, number)
            orientationView = typedArray.getInt(R.styleable.LikeView_lv_orientation, 0)
            isShadows = typedArray.getBoolean(R.styleable.LikeView_lv_shadows, isShadows)
        } finally {
            typedArray.recycle()
        }
        initView(context)

    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.view_like, this)
        linearLayout = view.findViewById(R.id.like_linear)
        imageView = view.findViewById(R.id.like_image_view)
        text = view.findViewById(R.id.like_image_text)
        like = number
        setShadows(isShadows)
        linearLayout!!.setOnClickListener(this)

        if (orientationView == 0) {
            linearLayout!!.orientation = LinearLayout.VERTICAL
        }

        if (orientationView == 1) {
            linearLayout!!.orientation = LinearLayout.HORIZONTAL

            val llp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            llp.setMargins(10, 0, 0, 0)
            text!!.layoutParams = llp
        }
    }

    fun setImage(@DrawableRes drawable: Int) {
        imageView!!.setImageDrawable(ContextCompat.getDrawable(context, drawable))
    }

    fun setShadows(b: Boolean) {
        isShadows = b
        if (b) {
            text!!.setShadowLayer(5f, 1f, 1f, Color.BLACK)
        } else {
            text!!.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
        }

    }

    override fun onClick(view: View) {
        val time = 200

        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = time.toLong()

        val translateAnimation = TranslateAnimation(0f, 0f, 0f, 0f)
        translateAnimation.duration = time.toLong()

        val set = AnimationSet(true)
        set.addAnimation(alphaAnimation)
        set.addAnimation(translateAnimation)
        linearLayout!!.startAnimation(set)
        number++
        like = number
    }
}
