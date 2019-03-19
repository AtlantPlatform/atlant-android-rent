/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.selected_app

import io.atlant.let.R
import io.atlant.let.activities.base.BasePresenter
import io.atlant.let.adapter.SelectedAppPagerAdapter
import io.atlant.let.adapter.SelectedAppPagerAdapter.CallBack
import io.atlant.let.model.SelectedApp
import java.util.*
import javax.inject.Inject

class SelectedAppPresenterImpl @Inject
internal constructor(private val view: SelectedAppView) : SelectedAppPresenter, BasePresenter, CallBack {

    override fun onCreate() {
        val context = view.context

        val arrayListTitle = ArrayList<SelectedApp>()

        arrayListTitle.add(SelectedApp(context.getString(R.string.selected_app_rent_title),
                context.getString(R.string.selected_app_rent_title2), R.drawable.start_rent))

        arrayListTitle.add(SelectedApp(context.getString(R.string.selected_app_invest_title),
                context.getString(R.string.selected_app_invest_title2), R.drawable.start_wallet))

        arrayListTitle.add(SelectedApp(context.getString(R.string.selected_app_trade_title),
                context.getString(R.string.selected_app_trade_title2),  R.drawable.start_exchange))

        val adapter = SelectedAppPagerAdapter(view.context, arrayListTitle)
        adapter.setCallBack(this)
        view.setAdapter(adapter)
    }

    override fun onClickItems(pos: Int) {

        if (pos == 0) {
            view.startRentals()
        }
        if (pos == 1) {
            view.startWallet()
        }
        if (pos == 2) {
            view.startTrade()
        }
    }
}

