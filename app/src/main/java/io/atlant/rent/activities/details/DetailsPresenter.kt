package io.atlant.rent.activities.details

import io.atlant.rent.activities.base.BasePresenter
import io.atlant.rent.model.Rent

interface DetailsPresenter : BasePresenter {

    fun onCreate(rent: Rent)

    fun startAnimationDown()

}
