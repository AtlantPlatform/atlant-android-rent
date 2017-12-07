package io.atlant.rent.activities.selected_app

import io.atlant.rent.R
import io.atlant.rent.activities.base.BasePresenter
import io.atlant.rent.adapter.SelectedAppPagerAdapter
import io.atlant.rent.adapter.SelectedAppPagerAdapter.CallBack
import io.atlant.rent.model.SelectedApp
import java.util.*
import javax.inject.Inject

class SelectedAppPresenterImpl @Inject
internal constructor(private val view: SelectedAppView) : SelectedAppPresenter, BasePresenter, CallBack {

    override fun onCreate() {
        val context = view.context
        val url1 = "https://atlant.io/promo/android/1.png"
        val url2 = "https://atlant.io/promo/android/3.png"
        val url3 = "https://atlant.io/promo/android/2.png"

        val arrayListTitle = ArrayList<SelectedApp>()

        arrayListTitle.add(SelectedApp(context.getString(R.string.selected_app_rent_title),
                context.getString(R.string.selected_app_rent_title2), url2))

        arrayListTitle.add(SelectedApp(context.getString(R.string.selected_app_invest_title),
                context.getString(R.string.selected_app_invest_title2), url1))

        arrayListTitle.add(SelectedApp(context.getString(R.string.selected_app_trade_title),
                context.getString(R.string.selected_app_trade_title2), url3))

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

