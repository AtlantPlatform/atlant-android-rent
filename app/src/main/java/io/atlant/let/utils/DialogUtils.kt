/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.View
import android.view.WindowManager
import io.atlant.let.view.DialogBookIt


class DialogUtils(private val context: Context) {
    private var dialog: Dialog? = null

    fun createDialogRentBookIt(message: String, listener: View.OnClickListener?) {
        Handler().post {
            dialog = DialogBookIt(context, message, listener)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
    }

    fun close() {
        try {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showDialogFullSize(scale: Float) {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y

        try {
            dialog!!.show()
            val lp = WindowManager.LayoutParams()
            val window = dialog!!.window!!
            lp.copyFrom(window.attributes)
            lp.width = (width * scale).toInt()
            lp.height = (height * scale).toInt()
            window.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun showDialog() {
        try {
            dialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
