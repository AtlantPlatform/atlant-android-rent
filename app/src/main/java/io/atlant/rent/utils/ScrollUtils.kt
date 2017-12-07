package io.atlant.rent.utils

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView

object ScrollUtils {

    fun canScroll(horizontalScrollView: HorizontalScrollView): Boolean {
        val child = horizontalScrollView.getChildAt(0) as View
        val childWidth = child.width
        return horizontalScrollView.width < childWidth + horizontalScrollView.paddingLeft + horizontalScrollView
                .paddingRight
    }

    fun canScroll(scrollView: ScrollView): Boolean {
        val child = scrollView.getChildAt(0) as View
        val childHeight = child.height
        return scrollView.height < childHeight + scrollView.paddingTop + scrollView.paddingBottom
    }

}
