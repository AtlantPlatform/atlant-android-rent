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
