package io.atlant.rent.activities.main

import io.atlant.rent.activities.base.BasePresenter

interface MainPresenter : BasePresenter {

    fun onCreate()

    fun onUpdateCity()

    fun onUpdate()

    fun onSelected(pos: Int)

    fun setCity(numberCity: Int)

    fun setCategory(numberCategory: Int)

}
