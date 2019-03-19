/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.main

import io.atlant.let.activities.base.BaseView
import io.atlant.let.model.Rent
import io.atlant.let.model.RentCity
import java.util.ArrayList

interface MainView : BaseView {

    fun setCategory(numberCategory: Int)

    fun setToolbar(arrayList: ArrayList<RentCity>)

    fun onUpdate(rents: ArrayList<Rent>)

    fun startActivity(rent: Rent)

}
