package io.atlant.rent.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object ScreenUtils {

    fun getWidth(context: Context): Int {
        val display = createWindowManager(context).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun getHeight(context: Context): Int {
        val display = createWindowManager(context).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    private fun createWindowManager(context: Context): WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}
