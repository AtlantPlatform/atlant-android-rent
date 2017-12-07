package io.atlant.rent.utils

import android.content.Context
import android.util.TypedValue

object DimensUtils {

    fun dpToPx(context: Context, dp: Float): Int {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics).toInt()
    }
}
