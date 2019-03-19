/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.main

import io.atlant.let.activities.base.BasePresenter

interface MainPresenter : BasePresenter {

    fun onCreate()

    fun onUpdateCity()

    fun onUpdate()

    fun onSelected(pos: Int)

    fun setCity(numberCity: Int)

    fun setCategory(numberCategory: Int)

}
