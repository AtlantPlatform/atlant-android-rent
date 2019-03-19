/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.utils

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
