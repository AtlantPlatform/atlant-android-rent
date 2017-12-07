package io.atlant.rent.activities.base

import android.content.Context

interface BaseView {

    val context: Context

    fun onRefreshStart()

    fun onRefreshUpdate()

    fun onRefreshComplete()

    fun onNoInternetConnection()

}
