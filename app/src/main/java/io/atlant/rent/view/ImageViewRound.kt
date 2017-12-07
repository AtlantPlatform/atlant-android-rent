package io.atlant.rent.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import io.atlant.rent.R

class ImageViewRound : android.support.v7.widget.AppCompatImageView {

    private var radius = 9.0f
    private var color = Color.TRANSPARENT

    private val paint = Paint()
    private val path = Path()

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ImageViewRound, 0, 0)
        try {
            radius = ta.getFloat(R.styleable.ImageViewRound_radius, radius)
            color = ta.getColor(R.styleable.ImageViewRound_color, color)
        } finally {
            ta.recycle()
        }
    }

    fun setColor(color: Int) {
        this.color = color
    }

    override fun onDraw(canvas: Canvas) {
        paint.isAntiAlias = true
        paint.color = color
        val rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.drawRoundRect(rect, radius, radius, paint)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}