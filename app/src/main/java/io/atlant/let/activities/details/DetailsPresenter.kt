/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.details

import io.atlant.let.activities.base.BasePresenter
import io.atlant.let.model.Rent

interface DetailsPresenter : BasePresenter {

    fun onCreate(rent: Rent)

    fun startAnimationDown()

}
