/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.selected_app

import io.atlant.let.activities.base.BaseView
import io.atlant.let.adapter.SelectedAppPagerAdapter

interface SelectedAppView : BaseView {

    fun setAdapter(adapter: SelectedAppPagerAdapter)

    fun startWallet()

    fun startRentals()

    fun startTrade()

}
