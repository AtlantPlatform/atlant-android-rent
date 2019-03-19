/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView

object FontsUtils {

    fun toOctarineBold(context: Context, text: TextView) {
        val custom_font = Typeface.createFromAsset(context.assets, "fonts/Octarine-Bold.otf")
        text.typeface = custom_font
    }
}
