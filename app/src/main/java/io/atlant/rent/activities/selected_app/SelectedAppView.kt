package io.atlant.rent.activities.selected_app

import io.atlant.rent.activities.base.BaseView
import io.atlant.rent.adapter.SelectedAppPagerAdapter

interface SelectedAppView : BaseView {

    fun setAdapter(adapter: SelectedAppPagerAdapter)

    fun startWallet()

    fun startRentals()

    fun startTrade()

}
