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
