/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import io.atlant.let.R
import io.atlant.let.adapter.RentCityAdapter
import io.atlant.let.model.RentCity
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class ToolbarMainView : LinearLayout, RentCityAdapter.CallBack {

    private var callback: CallBack? = null

    private val layoutRes: Int
        get() = R.layout.view_toolbar_main

    override fun onCityClick(pos: Int) {
        if (callback != null) {
            callback!!.onCityClick(pos)
        }
    }

    interface CallBack {

        fun onCityClick(pos: Int)

    }

    fun setCallback(callback: CallBack) {
        this.callback = callback
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layoutRes, this)
    }


    fun setContent(arrayList: ArrayList<RentCity>?) {
        try {
            if (arrayList != null && arrayList.size > 0) {

                val rentCityAdapter = RentCityAdapter(arrayList)
                rentCityAdapter.setCallBack(this)
                val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                main_recycler_view.layoutManager = manager
                main_recycler_view.setHasFixedSize(true)
                main_recycler_view.adapter = rentCityAdapter
                rentCityAdapter.setCallBack(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
