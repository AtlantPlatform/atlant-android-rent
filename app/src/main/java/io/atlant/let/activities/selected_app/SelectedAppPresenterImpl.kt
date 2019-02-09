/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
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

