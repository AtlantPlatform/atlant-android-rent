/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.utils

import android.content.Context
import android.util.TypedValue

object DimensUtils {

    fun dpToPx(context: Context, dp: Float): Int {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics).toInt()
    }
}
