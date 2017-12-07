package io.atlant.rent.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView

object FontsUtils {

    fun toOctarineBold(context: Context, text: TextView) {
        val custom_font = Typeface.createFromAsset(context.assets, "fonts/Octarine-Bold.otf")
        text.typeface = custom_font
    }
}
