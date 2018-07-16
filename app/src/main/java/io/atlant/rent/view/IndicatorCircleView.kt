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

package io.atlant.rent.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import io.atlant.rent.R

class IndicatorCircleView : LinearLayout, ViewPager.OnPageChangeListener {

    private val paint = Paint()
    private var radius = 50
    private var margin = 10
    private var max = 5
    private var colorNormal = Color.WHITE
    private var colorSelected = Color.BLACK
    private var current = 3
    private var k = 0

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IndicatorCircleView)
        try {
            radius = typedArray.getDimension(R.styleable.IndicatorCircleView_icv_radius, radius.toFloat()).toInt()
            margin = typedArray.getDimension(R.styleable.IndicatorCircleView_icv_margin, margin.toFloat()).toInt()
            max = typedArray.getInteger(R.styleable.IndicatorCircleView_icv_max, max)
            current = typedArray.getInteger(R.styleable.IndicatorCircleView_icv_current, current)
            current--
            colorNormal = typedArray.getColor(R.styleable.IndicatorCircleView_icv_color_normal, colorNormal)
            colorSelected = typedArray.getColor(R.styleable.IndicatorCircleView_icv_color_selected, colorSelected)
        } finally {
            typedArray.recycle()
        }
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.view_indicator_circle_view_page, this)
        val linearLayout = view.findViewById<LinearLayout>(R.id.view_indicator_circle_view_page_layout)
        linearLayout.removeAllViews()

        for (i in k until max - k) {
            if (i == current) {
                linearLayout.addView(getItemView(context, radius, margin, colorSelected))
            } else {
                linearLayout.addView(getItemView(context, radius, margin, colorNormal))
            }
        }
    }

    private fun getItemView(context: Context, size: Int, margin: Int, color: Int): View {
        val layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
        val view = ViewCircle(context)
        view.setCurrentColor(color)
        view.layoutParams = layoutParams
        return view
    }

    fun setViewPager(viewPager: ViewPager) {
        k = 0
        viewPager.addOnPageChangeListener(this)
        max = viewPager.adapter!!.count
        current = viewPager.currentItem
        update()
    }

    /**
     * For circular adapter, where 2 fake page (first and last)
     */
    fun setViewPagerCircle(viewPager: ViewPager) {
        k = 1
        viewPager.addOnPageChangeListener(this)
        max = viewPager.adapter!!.count
        current = viewPager.currentItem
        if (current == 0) {
            current++
            viewPager.currentItem = current
        }
        if (current == max) {
            current--
            viewPager.currentItem = current
        }
        update()
    }

    private fun update() {
        initView(context)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        current = position

        //for a circular adapter
        if (k == 1) {
            if (current == 0) {
                current++
            }
            if (current == max) {
                current--
            }
        }
        update()
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    internal inner class ViewCircle : View {

        private var currentColor: Int = 0

        constructor(context: Context) : super(context) {}

        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

        fun setCurrentColor(currentColor: Int) {
            this.currentColor = currentColor
        }

        override fun onDraw(canvas: Canvas) {
            paint.isAntiAlias = true
            paint.color = currentColor
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(), paint)
            super.onDraw(canvas)
        }
    }

}
