/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.base

import android.content.Context

interface BaseView {

    val context: Context

    fun onRefreshStart()

    fun onRefreshUpdate()

    fun onRefreshComplete()

    fun onNoInternetConnection()

}
