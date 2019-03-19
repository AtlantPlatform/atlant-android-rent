/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import io.atlant.let.R

import io.atlant.let.utils.FontsUtils
import kotlinx.android.synthetic.main.dialog_book_it.*
import kotlinx.android.synthetic.main.view_name.*

class DialogBookIt(context: Context, private val message: String, private val listener: View.OnClickListener?) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_book_it)
        app_name.text = context.getString(R.string.app_name)
        FontsUtils.toOctarineBold(context, app_name)
        dialog_book_it_message.text = message
        dialog_book_it_close.setOnClickListener(onClickClose)
        dialog_book_it_web.setOnClickListener(onClickWeb)
    }

    private val onClickWeb = View.OnClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.atlant.io"))
        context.startActivity(browserIntent)
    }

    private val onClickClose = View.OnClickListener { view ->
        listener?.onClick(view)
        dismiss()
    }

}